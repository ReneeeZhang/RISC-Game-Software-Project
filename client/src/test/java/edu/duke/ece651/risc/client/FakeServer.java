package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import shared.Board;
import shared.Initializer;
import shared.Region;

public class FakeServer implements Runnable {
  public void run() {
    try{
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.socket().bind(new InetSocketAddress(6666));
      SocketChannel sc = ssc.accept();
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject("Player1");
      Initializer init = new Initializer(2);
      Board board = init.initGame();
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(board);
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      deserial.readObject();
      board.move("Perkins", "Teer", 5);
      board.move("Hudson", "Wilson", 5);
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(board);
      deserial = new ObjectInputStream(s.getInputStream());
      deserial.readObject();
      board.move("Teer", "Bostock", 10);
      board.move("Fitzpatrick", "Wilson", 4);
      board.attack("Wilson", "Teer", 10);
      board.attack("Fitzpatrick", "Perkins", 1);
      board.resolve();
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(board);
      deserial = new ObjectInputStream(s.getInputStream());
      deserial.readObject();
      Region r = board.getRegion("Teer");
      for (int i = 0; i < 20; i++) {
        r.autoIncrement();
      }
      board.attack("Bostock", "Teer", 15);
      board.attack("Teer", "Bostock", 1);
      board.resolve();
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(board);
      deserial = new ObjectInputStream(s.getInputStream());
      deserial.readObject();
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }
}
