package edu.duke.ece651.risc.chatServer;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
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
    rooms = new HashMap<Integer, ChatRoom>();
  }
  
  public static void main(String[] args){
    try{
      ChatServer server = ChatServer.start("src/main/resources/config.txt");
      while (true) {
        server.handleRequest();
      }
    } catch (Exception e) {
    }
  }

  public static ChatServer start(String path) throws IOException {
    Scanner config = new Scanner(new File("/src/main/resources"));
    return new ChatServer(config.nextInt());
  }

  public void handleRequest() throws IOException, ClassNotFoundException {
    SocketChannel sc = ssc.accept();
    int num = getPlayerNum(sc);
    ChatRoom room = getChatRoom(num);
    room.addPlayer(sc);
    if (room.isFull()) {
      Thread chatRoom = new Thread(room);
      chatRoom.start();
    }
  }

  public int getPlayerNum(SocketChannel sc) throws IOException, ClassNotFoundException{
    Socket s = sc.socket();
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    Integer playerNum = (Integer) deserial.readObject();
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
