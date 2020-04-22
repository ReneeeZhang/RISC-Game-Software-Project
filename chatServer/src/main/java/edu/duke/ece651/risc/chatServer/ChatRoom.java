package edu.duke.ece651.risc.chatServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChatRoom implements Runnable {
  private int num;
  private List<SocketChannel> sockets;

  public ChatRoom(int num) throws IOException {
    this.num = num;
    sockets = new ArrayList<SocketChannel>();
  }

  public void run() {
    try{
      while (true) {
        
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public boolean isFull() {
    return sockets.size() == num;
  }

  public void addPlayer(SocketChannel sc) {
    sockets.add(sc);
  }
  

  public Selector setUpSelector() throws IOException {
    Selector selector = Selector.open();
    for (SocketChannel sc : sockets) {
      sc.configureBlocking(false);
      sc.register(selector, SelectionKey.OP_READ);
      sockets.remove(sc);
    }
    return selector;
  }

  public void select(Selector selector) throws IOException, ClassNotFoundException{
    setUpSelector();
    selector.select();
    Set<SelectionKey> keys = selector.selectedKeys();
    Iterator<SelectionKey> keyIterator = keys.iterator();
    while (keyIterator.hasNext()) {
      SelectionKey key = keyIterator.next();
      if (key.isReadable()) {
        SocketChannel sc = (SocketChannel) key.channel();
        // send to all
        String msg = recvMessage(sc);
        sendToAll(msg);
      }
      keyIterator.remove();
    }
  }
  
  public String recvMessage(SocketChannel sc) throws IOException, ClassNotFoundException {
    sc.configureBlocking(true);
    Socket s = sc.socket();
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    return (String) deserial.readObject();
  }

  public void sendToAll(String msg) throws IOException {
    for (SocketChannel sc : sockets) {
      sc.configureBlocking(true);
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(msg);
    }
  }
}
