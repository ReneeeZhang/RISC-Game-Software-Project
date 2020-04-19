package edu.duke.ece651.risc.server;

import org.junit.jupiter.api.Test;

public class ServerTest {
  @Test
  public void test_Server() {
    try{
      Server server = new Server(7777);
      Thread fc1 = new Thread(new FakeClient("Hudson\nFitzpatrick\n0\n3\nHudson\nWilson\n0\n2\n"));
      Thread fc2 = new Thread(new FakeClient("Wilson\nBostock\n0\n5\nTeer\nPerkins\n0\n5\n"));
      fc1.start();
      fc2.start();
      for (int i = 0; i < 2; i++) {
        Thread fc = new Thread(new FakeClient(""));
        fc.start();
      }
      for (int i = 0; i < 4; i++) {
        server.handleRequest();
      }
    } catch (Exception e) {
    }
  }
}
