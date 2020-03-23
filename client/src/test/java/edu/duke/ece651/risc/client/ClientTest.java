package edu.duke.ece651.risc.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import shared.*;

import org.junit.jupiter.api.Test;

public class ClientTest implements Runnable{
  @Test
  public void test_client() {
    Thread serverThread = new Thread(new ClientTest());
    serverThread.start();
    try{
      System.setIn(new FileInputStream("src/resource/ClientTest.txt"));
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
    Client.main(new String[] { "localhost", "6666" });
  }

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
