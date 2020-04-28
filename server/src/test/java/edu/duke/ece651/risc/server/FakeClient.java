package edu.duke.ece651.risc.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import shared.GameBoard;
import shared.instructions.Instruction;

public class FakeClient implements Runnable {
  private List<Instruction> instr;
  
  public FakeClient(List<Instruction> instr) {
    this.instr = instr;
  }

  public void run() {
    try {
      Thread.sleep(1000);
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 7777));
      Socket s = sc.socket();
      
      // send player number to server
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(2);
      
      // recv player name from server
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      String name = (String) deserial.readObject();

      // recv board from server
      deserial = new ObjectInputStream(s.getInputStream());
      GameBoard b = (GameBoard) deserial.readObject();
      
      // send instr to server
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(new ArrayList<>());

      // recv board from server
      deserial = new ObjectInputStream(s.getInputStream());
      b = (GameBoard) deserial.readObject();

      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(instr);
      
      sc.close();
      return;
    } catch (Exception e) {
      return;
    }
  }
}
