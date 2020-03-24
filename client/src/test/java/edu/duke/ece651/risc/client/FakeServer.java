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
      board.move("Bostock", "Teer", 5);
      board.attack("Teer", "Fitzpatrick", 10);
      board.move("Hudson", "Wilson", 2);
      board.attack("Wilson", "Bostock", 5);
      board.resolve();
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(board);
      deserial = new ObjectInputStream(s.getInputStream());
      board.move("Fitzpatrick", "Bostock", 5);
      board.attack("Fitzpatrick", "Perkins", 1);
      board.move("Perkins", "Teer", 6);
      board.attack("Teer", "Bostock", 7);
      board.resolve();
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(board);
      deserial = new ObjectInputStream(s.getInputStream());
      board.move("Hudson", "Wilson", 1);
      board.attack("Bostock", "Teer", 10);
      board.move("Teer", "Teer", 0);
      board.attack("Teer", "Bostock", 1);
      board.resolve();
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(board);
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
