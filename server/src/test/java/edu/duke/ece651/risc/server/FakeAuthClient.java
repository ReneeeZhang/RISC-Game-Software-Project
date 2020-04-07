package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class FakeAuthClient implements Runnable {
  private String login;
  private String ans;
  
  public FakeAuthClient(String login, String ans) {
    this.login = login;
    this.ans = ans;
  }
  
  public void run() {
    try {
      Thread.sleep(50);
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 6666));
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(login);
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      String res = (String) deserial.readObject();
      assertTrue(res.equals(ans));
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
