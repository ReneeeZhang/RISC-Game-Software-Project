package edu.duke.ece651.risc.chatServer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatServer {
  private ServerSocketChannel ssc;
  private Map<Integer, ChatRoom> rooms;

  public ChatServer(int port) throws IOException{
    ssc = ServerSocketChannel.open();
    ssc.socket().bind(new InetSocketAddress(port));
    rooms = new HashMap<Integer, ChatRoom>();
    for (int i = 2; i < 6; i++) {
      rooms.put(i, new ChatRoom(i));
    }
  }
  
  public static void main(String[] args){
    try{
      ChatServer server = ChatServer.start("src/main/resources/config.txt");
      while (true) {
        server.handleRequest();
        System.out.println("handle request suc");
      }
    } catch (Exception e) {
    }
  }

  public static ChatServer start(String path) throws IOException {
    Scanner config = new Scanner(new File(path));
    return new ChatServer(config.nextInt());
  }

  public void handleRequest() throws IOException, ClassNotFoundException {
    SocketChannel sc = ssc.accept();
    ObjectOutputStream oos = new ObjectOutputStream(sc.socket().getOutputStream());
    System.out.println("oos");
    ObjectInputStream ois = new ObjectInputStream(sc.socket().getInputStream());
    System.out.println("ois");
    int num = getPlayerNum(ois);
    System.out.println("Chat server get player num: " + num);
    ChatRoom room = getChatRoom(num);
    System.out.println("get room");
    room.addPlayer(sc, ois, oos);
    System.out.println("add player");
    if (room.isFull()) {
      Thread chatRoom = new Thread(room);
      System.out.println("Chat room starting");
      chatRoom.start();
    }
  }

  public int getPlayerNum(ObjectInputStream ois) throws IOException, ClassNotFoundException{
    Integer playerNum = (Integer) ois.readObject();
    return playerNum;
  }

  public ChatRoom getChatRoom(int num) throws IOException {
    ChatRoom room = rooms.get(num);
    if (room.isFull()) {
      room = new ChatRoom(num);
      rooms.put(num, room);
    }
    return room;
  }
}
