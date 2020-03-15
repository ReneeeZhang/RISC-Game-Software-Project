package edu.duke.ece651.risc.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import shared.*;

public class Server {
  private Board board;
  private ServerSocket ss;
  private List<Socket> clients;
  
  public Server(int port) throws IOException {
    this.board = new GameBoard();
    this.ss = new ServerSocket(port);
    this.clients = new ArrayList<Socket>();
  }

  public static void main(String[] args) {
    try{
      Server server = new Server(Integer.valueOf(args[0]));
      server.waitForClients(1);
      System.out.println("wait ends");
      server.sendToClient();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void waitForClients(int playerNum) throws IOException{
    for (int i = 0; i < playerNum; i++) {
      Socket s = ss.accept();
      System.out.println("accept");
      clients.add(s);
    }
  }

  public void sendToClient() throws IOException{
    for (Socket s : clients) {
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());
      ObjectOutputStream serial = new ObjectOutputStream(dout);
      serial.writeObject(this.board);
      //ObjectMapper mapper = new ObjectMapper();
      //mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
      //dout.write(mapper.writeValueAsBytes(this.board));
    }
  }
}
