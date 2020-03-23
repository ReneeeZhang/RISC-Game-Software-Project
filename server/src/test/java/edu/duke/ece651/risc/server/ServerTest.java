package edu.duke.ece651.risc.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.*;

import org.junit.jupiter.api.Test;

import shared.*;

public class ServerTest implements Runnable {
  @Test
  public void test_server() {
    Thread player1 = new Thread(new ServerTest());
    Thread player2 = new Thread(new ServerTest());
    player1.start();
    player2.start();
    try{
      System.setIn(new FileInputStream("src/test/resources/ServerTest.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
    Server.main(new String[] { "6666" });
  }

  public void run() {
    try {
      Thread.sleep(5);
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 6666));
      Socket s = sc.socket();
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      GameBoard b = (GameBoard) deserial.readObject();
      System.out.println(b.draw());
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      List<Instruction> ins = new ArrayList<Instruction>();
      Instruction move = new Move("Bostock", "Teer", 2);
      ins.add(move);
      //Instruction attack = new Attack("Teer", "Hudson", 2);
      //ins.add(attack);
      serial.writeObject(ins);
    }
    catch (Exception e) {
      System.out.println(e);
    }
  }
}
