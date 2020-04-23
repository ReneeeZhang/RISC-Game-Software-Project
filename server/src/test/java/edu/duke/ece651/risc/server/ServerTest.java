package edu.duke.ece651.risc.server;

import org.junit.jupiter.api.Test;

public class ServerTest {
  @Test
  public void test_Server() {
    try {
      Thread fc1 = new Thread(new FakeClient("Player1\nHudson\nWilson\n0\n1\nPlayer1\nWilson\nBostock\n0\n2\n"));
      Thread fc2 = new Thread(new FakeClient("Player2\nTeer\nBostock\n0\n1\nPlayer2\nPerkins\nFitzpatrick\n0\n1\n"));
      //Thread fc3 = new Thread(new FakeClient(""));
      //Thread fc4 = new Thread(new FakeClient(""));
      fc1.start();
      fc2.start();
      //fc3.start();
      //fc4.start();
      Server server = Server.start("src/main/resources/config.txt");
      for (int i = 0; i < 2; i++) {
        server.handleRequest();
      }
      System.out.println("success");
      Thread.sleep(5000);
    } catch (Exception e) {
    }
  }
}
