package edu.duke.ece651.risc.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shared.*;

public class FakeClient implements Runnable {
  private Scanner sc;

  public FakeClient(Scanner sc) {
    this.sc = sc;
  }
  
  public void run() {
    try {
      Thread.sleep(5);
      SocketChannel socketChannel = SocketChannel.open();
      socketChannel.connect(new InetSocketAddress("localhost", 6666));
      Socket s = socketChannel.socket();
      while (true) {
        ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
        GameBoard b = (GameBoard) deserial.readObject();
        System.out.println(b.draw());
        ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
        List<Instruction> ins = new ArrayList<Instruction>();
        Instruction move = new Move(sc.nextLine(), sc.nextLine(), Integer.valueOf(sc.nextLine()));
        ins.add(move);
        Instruction attack = new Attack(sc.nextLine(), sc.nextLine(), Integer.valueOf(sc.nextLine()));
        ins.add(attack);
        serial.writeObject(ins);
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }
}
