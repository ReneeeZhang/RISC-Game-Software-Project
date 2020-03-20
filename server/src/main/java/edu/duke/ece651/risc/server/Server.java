package edu.duke.ece651.risc.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import shared.*;

public class Server {
  private ServerSocket ss;

  public Server() throws IOException {
    this.ss = new ServerSocket(6666);
  }
  
  public Server(int port) throws IOException {
    this.ss = new ServerSocket(port);
  }
  
  public static void main(String[] args) {
    try {
      Server server = new Server(Integer.valueOf(args[0]));
      System.out.println("Create a new game for how many players:");
      int playerNum = Integer.valueOf(System.in.toString());
      List<Socket> players = server.waitForClients(playerNum);
      // TODO: GM constructor
      GameMaster gm = new GameMaster(players);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
  
  public List<Socket> waitForClients(int playerNum) throws IOException {
    List<Socket> clients = new ArrayList<Socket>();
    for (int i = 0; i < playerNum; i++) {
      Socket s = ss.accept();
      System.out.println("accept");
      clients.add(s);
    }
    return clients;
  }
}
