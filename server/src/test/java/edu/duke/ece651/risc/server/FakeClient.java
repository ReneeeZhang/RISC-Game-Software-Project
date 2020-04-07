package edu.duke.ece651.risc.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

import shared.GameBoard;
import shared.instructions.Instruction;

public class FakeClient implements Runnable {
  public FakeClient() {
  }
  
  public void run() {
    try {
      Thread.sleep(50);
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 7777));
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(2);
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      String name = (String) deserial.readObject();
      System.out.println(name);
      while (true) {
        deserial = new ObjectInputStream(s.getInputStream());
        GameBoard b = (GameBoard) deserial.readObject();
        //System.out.println(b.draw());
        serial = new ObjectOutputStream(s.getOutputStream());
        serial.writeObject(new ArrayList<Instruction>());
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
