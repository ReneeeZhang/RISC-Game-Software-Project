package edu.duke.ece651.risc.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import shared.Board;
import shared.Instruction;
import shared.Move;

public class Client {
  private Socket s;

  public Client(String hostname, int port) throws IOException {
    this.s = new Socket(hostname, port);
  }
  
  public void receiveFromServer() throws IOException {
    DataInputStream din = new DataInputStream(s.getInputStream());
    ObjectInputStream deserial = new ObjectInputStream(din);
    try{
      Board b = (Board) deserial.readObject();
      System.out.println(b);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }

  public void sendToServer(Instruction inst) throws IOException {
    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
    ObjectOutputStream serial = new ObjectOutputStream(dout);
    serial.writeObject(inst);
  }

  public static void main(String[] args) {
    try {
      Client client = new Client(args[0], Integer.valueOf(args[1]));
      System.out.println("Connected");
      client.receiveFromServer();
      // Hardcode an instruction
      Instruction inst = new Move("Fitzpatrick", "Teer", 1);
      client.sendToServer(inst);
      client.receiveFromServer();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
