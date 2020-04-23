package edu.duke.ece651.risc.chatServer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ChatServerTest {
  @Test
  public void test_ChatServer() {
    try{
      Thread fakeClient1 = new Thread(new FakeClient("mesg1"));
      fakeClient1.start();
      for (int i = 0; i < 3; i++) {
        Thread fakeClient = new Thread(new FakeClient(""));
        fakeClient.start();
      }
      ChatServer server = ChatServer.start("src/main/resources/config.txt");
      for (int i = 0; i < 4; i++) {
        server.handleRequest();
      }
    } catch (Exception e) {
    }
  }
}
