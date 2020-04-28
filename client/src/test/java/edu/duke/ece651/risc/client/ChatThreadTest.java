package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

public class ChatThreadTest {
  @Test
  public void test_() {
    ClientGUI gui = mock(ClientGUI.class);
    ChatThread ct = new ChatThread(gui, null, 0);
    ct.changeRoom(1);
  }

}
