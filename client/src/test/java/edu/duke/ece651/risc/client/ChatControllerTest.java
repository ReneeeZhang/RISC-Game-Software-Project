package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ChatControllerTest {
  @Test
  public void test_ChatController() {
    Thread chatServerThread = new Thread(new FakeChatServer(8888));
    chatServerThread.start();
    try{
      Thread.sleep(1000);
      ChatController cc = new ChatController("localhost", 8888);
      cc.send("msg1");
      String msg1 = (String) cc.receive();
      assertEquals(msg1, "msg1");
    }
    catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

}
