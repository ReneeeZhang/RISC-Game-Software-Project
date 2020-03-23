package edu.duke.ece651.risc.server;

import java.io.IOException;
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
    //System.out.println("Server Test");
    try {
      Server server = new Server(6666);
      Thread player1 = new Thread(new ServerTest());
      Thread player2 = new Thread(new ServerTest());
      player1.start();
      player2.start();
      List<SocketChannel> players = server.waitForClients(2);
      GameMaster gm = new GameMaster(players);
      gm.sendBoardToClient();
      gm.recvInstrFromClient();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void run() {
    try{
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 6666));
      Socket s = sc.socket();
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      GameBoard b = (GameBoard) deserial.readObject();
      System.out.println(b.draw());
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      Instruction move = new Move("Hudson", "Teer", 1);
      Instruction attack = new Attack("Teer", "Hudson", 2);
      List<Instruction> ins = new ArrayList<Instruction>();
      ins.add(move);
      ins.add(attack);
      serial.writeObject(ins);
    }
    catch (ClassNotFoundException e) {
      System.out.println(e);
    }
    catch (IOException e) {
      System.out.println(e);
    }
  }
}
