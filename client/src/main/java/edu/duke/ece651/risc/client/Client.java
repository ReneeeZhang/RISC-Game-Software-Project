package edu.duke.ece651.risc.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import shared.*;

public class Client {
  private Socket s;

  public Client(String hostname, int port) throws IOException{
    this.s = new Socket(hostname, port);
  }

  public static void main(String[] args){
    try{
      Client client = new Client(args[0], Integer.valueOf(args[1]));
      System.out.println("Connected");
      client.receive();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
  
  public void receive() throws IOException{
    DataInputStream din = new DataInputStream(s.getInputStream());
    ObjectInputStream deserial = new ObjectInputStream(din);
    try{
      Board b = (GameBoard) deserial.readObject();
      System.out.println(b);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }
}
