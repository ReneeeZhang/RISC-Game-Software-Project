package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import org.junit.jupiter.api.Test;

public class GameMasterTest {
  @Test
  public void test_GameMaster() {
    try{
      GameMaster gm = new GameMaster(2);
      assertFalse(gm.isFull());
      SocketChannel sc = mock(SocketChannel.class);
      Socket s = mock(Socket.class);
      ObjectInputStream deserial = mock(ObjectInputStream.class);
      when(sc.socket()).thenReturn(s);
      when(s.getInputStream()).thenReturn(deserial);
      when(deserial.readObject()).thenReturn("yes");
      gm.recvYesFromClient(sc);
    } catch (Exception e) {
    }
  }
}
