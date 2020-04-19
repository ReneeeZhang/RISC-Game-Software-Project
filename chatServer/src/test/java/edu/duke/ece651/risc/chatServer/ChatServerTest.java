package edu.duke.ece651.risc.chatServer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ChatServerTest {
  @Test
  public void test_ChatServer() {
    try{
      ChatServer server = new ChatServer(8888);
    } catch (Exception e) {
    }
  }
}
