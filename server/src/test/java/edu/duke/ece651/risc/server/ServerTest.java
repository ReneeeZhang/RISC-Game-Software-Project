package edu.duke.ece651.risc.server;

import org.junit.jupiter.api.Test;

public class ServerTest {
  @Test
  public void test_Server() {
    try{
      Server server = new Server(7777);
      for (int i = 0; i < 4; i++) {
        Thread fc = new Thread(new FakeClient());
        fc.start();
      }
      for (int i = 0; i < 4; i++) {
        server.handleRequest();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
