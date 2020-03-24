package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import shared.GameBoard;

public class FakeServer implements Runnable {
  public void run() {
    try{
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.socket().bind(new InetSocketAddress(6666));
      SocketChannel sc = ssc.accept();
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(new GameBoard());
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
