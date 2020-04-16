package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AuthServerTest {
  private static AuthServer auth;

  @BeforeAll
  public static void startAuthServer() {
    try {
      auth = new AuthServer(6666);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
  
  @Test
  public void test_AuthServer() {
    Thread fake1 = new Thread(new FakeAuthClient("user&&passw0rd", "yes"));
    Thread fake2 = new Thread(new FakeAuthClient("user&&password", "no"));
    fake1.start();
    fake2.start();
    for (int i = 0; i < 2; i++) {
      Socket s = auth.acceptSocket();
      try{
        auth.handleRequest(s);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }

  @AfterAll
  public static void close_AuthServer() {
    try{
      auth.close();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
