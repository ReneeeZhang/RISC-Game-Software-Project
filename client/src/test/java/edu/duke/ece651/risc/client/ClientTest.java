package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.junit.jupiter.api.Test;

public class ClientTest implements Runnable{
  @Test
  public void test_client() {
    try {
      Thread serverThread = new Thread(new ClientTest());
      serverThread.start();
      Client client = new Client("localhost", 6666);
      client.run();
    } catch(IOException e) {
      System.out.println(e);
    }
  }

  public void run() {
    try{
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.socket().bind(new InetSocketAddress(6666));
      SocketChannel sc = ssc.accept();
      Socket s = sc.socket();
    } catch (IOException e) {
      System.out.println(e);
    }
    
  }
}
