package edu.duke.ece651.risc.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import shared.Board;
import shared.GameBoard;
import shared.Instruction;
import shared.Move;

public class Client {
  private Socket s;

  public Client(String hostname, int port) throws IOException {
    SocketChannel sc = SocketChannel.open();
    sc.connect(new InetSocketAddress(hostname, port));
    this.s = sc.socket();
  }
  
  public GameBoard receiveFromServer() throws IOException, ClassNotFoundException {
    DataInputStream din = new DataInputStream(s.getInputStream());
    ObjectInputStream deserial = new ObjectInputStream(din);
    return (GameBoard) deserial.readObject();
  }

  public void sendToServer(Instruction inst) throws IOException {
    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
    ObjectOutputStream serial = new ObjectOutputStream(dout);
    serial.writeObject(inst);
  }

  public void run() {
    try {
      while (true) {
        // receive the board from GameMaster
        //GameBoard board = receiveFromServer();
      }
    }
    catch(Exception e){
      System.out.println(e);
    }
  }

  public void generateInst() {

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
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
