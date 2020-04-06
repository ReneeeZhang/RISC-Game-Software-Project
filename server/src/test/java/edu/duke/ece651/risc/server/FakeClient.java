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
  public FakeClient() {
  }
  
  public void run() {
    try {
      Thread.sleep(50);
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 6666));
      Socket s = sc.socket();
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      String name = (String) deserial.readObject();
      System.out.println(name);
      while (true) {
        deserial = new ObjectInputStream(s.getInputStream());
        GameBoard b = (GameBoard) deserial.readObject();
        System.out.println(b.draw());
        ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
        List<Instruction> ins = new ArrayList<Instruction>();
        serial.writeObject(ins);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
