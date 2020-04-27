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

public class FakeGameServer implements Runnable {
  private ServerSocketChannel ssc;
  private Socket socket;

  public FakeGameServer(int port) {
    try {
      System.out.println("Constructing fake server");
      ssc = ServerSocketChannel.open();
      ssc.socket().bind(new InetSocketAddress(port));
      
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }


  public void run() {
    try {
      SocketChannel sc = ssc.accept();
      System.out.println("accepted");
      this.socket = sc.socket();
      int num = (Integer) receive();
      send("good");
      receive();
      /*
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
      */
    } catch (IOException e) {
      System.out.println(e);
    }
    catch (ClassNotFoundException e) {
      System.out.println(e);
    }
    
  }


    private Object receive() throws IOException, ClassNotFoundException {
    ObjectInputStream deserial = new ObjectInputStream(socket.getInputStream());
    return deserial.readObject();
  }

  private void send(Object obj) throws IOException {
    ObjectOutputStream serial = new ObjectOutputStream(socket.getOutputStream());
    serial.writeObject(obj);
  }
}
