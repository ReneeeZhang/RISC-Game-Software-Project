package edu.duke.ece651.risc.server;

import java.net.Socket;

import org.junit.jupiter.api.Test;

public class AuthServerTest {
  @Test
  public void test_AuthServer() {
    Thread fake1 = new Thread(new FakeAuthClient("user&&passw0rd", "yes"));
    Thread fake2 = new Thread(new FakeAuthClient("user&&password", "no"));
    fake1.start();
    fake2.start();
    try {
      AuthServer auth = new AuthServer(6666);
      for (int i = 0; i < 2; i++) {
        Socket s = auth.acceptSocket();
        auth.handleRequest(s);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
