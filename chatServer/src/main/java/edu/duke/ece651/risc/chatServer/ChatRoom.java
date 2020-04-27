package edu.duke.ece651.risc.chatServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChatRoom implements Runnable {
  private int num;
  private List<SocketChannel> sockets;
  private Map<SocketChannel, ObjectInputStream> inputs;
  private Map<SocketChannel, ObjectOutputStream> outputs;
  private Selector selector;

  public ChatRoom(int num) throws IOException {
    this.num = num;
    sockets = new ArrayList<SocketChannel>();
    inputs = new HashMap<>();
    outputs = new HashMap<>();
  }

  public void run() {
    try {
      while (true) {
        System.out.println("Chat room starts");
        setUpSelector();
        select();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean isFull() {
    return sockets.size() == num;
  }

  public void addPlayer(SocketChannel sc, ObjectInputStream ois, ObjectOutputStream oos) throws IOException{
    sockets.add(sc);
    outputs.put(sc, oos);
    inputs.put(sc, ois);
  }
  
  public void setUpSelector() throws IOException {
    selector = Selector.open();
    for (SocketChannel sc : sockets) {
      sc.configureBlocking(false);
      sc.register(selector, SelectionKey.OP_READ);
    }
  }

  public void select() throws IOException, ClassNotFoundException{
    selector.select();
    Set<SelectionKey> keys = selector.selectedKeys();
    Iterator<SelectionKey> keyIterator = keys.iterator();
    String mesg = "";
    while (keyIterator.hasNext()) {
      SelectionKey key = keyIterator.next();
      keyIterator.remove();
      if (key.isReadable()) {
        key.cancel();
        SocketChannel sc = (SocketChannel) key.channel();
        sc.configureBlocking(true);
        mesg = recvMessage(sc);
      }
    }
    selector.close();
    sendToAll(mesg);
  }
  
  public String recvMessage(SocketChannel sc) throws IOException, ClassNotFoundException {
    sc.configureBlocking(true);
    String mesg = (String) inputs.get(sc).readObject();
    System.out.println(mesg);
    return mesg;
  }

  public void sendToAll(String msg) throws IOException {
    for (SocketChannel sc : sockets) {
      outputs.get(sc).writeObject(msg);
    }
  }
}
