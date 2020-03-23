package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import org.junit.jupiter.api.Test;

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
      server.waitForClients(2);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void run() {
    try{
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 6666));
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
