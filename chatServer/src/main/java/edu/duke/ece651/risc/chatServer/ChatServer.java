package edu.duke.ece651.risc.chatServer;

import java.io.File;
import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.util.Scanner;

public class ChatServer {
  private ServerSocketChannel s;

  public ChatServer(int port) throws IOException{
    s = ServerSocketChannel.open();
  }
  
  public static void main(String[] args){
    try{
      Scanner config = new Scanner(new File("/src/main/resources"));
      ChatServer server = new ChatServer(config.nextInt());
      while (true) {
        // TODO: make chat rooms for players
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
