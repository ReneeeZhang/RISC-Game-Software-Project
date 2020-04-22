package edu.duke.ece651.risc.server;

import org.junit.jupiter.api.Test;

public class ServerTest {
  @Test
  public void test_Server() {
    try {
      Server server = Server.start("src/main/resources/config.txt");
      Thread fc1 = new Thread(new FakeClient("Player1\nHudson\nFitzpatrick\n0\n1\nPlayer1\nHudson\nWilson\n0\n2\n"));
      Thread fc2 = new Thread(new FakeClient("Player2\nWilson\nBostock\n0\n1\nPlayer2\nTeer\nPerkins\n0\n1\n"));
      Thread fc3 = new Thread(new FakeClient(""));
      //Thread fc4 = new Thread(new FakeClient(""));
      fc1.start();
      fc2.start();
      fc3.start();
      for (int i = 0; i < 4; i++) {
        server.handleRequest();
      }
    } catch (Exception e) {
    }
  }
}
