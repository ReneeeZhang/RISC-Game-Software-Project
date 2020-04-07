package edu.duke.ece651.risc.server;

import java.nio.channels.SocketChannel;

import org.junit.jupiter.api.Test;

public class ServerTest {
  //@Test
  public void test_Server() {
    try{
      Server server = new Server(6666);
      Thread fc1 = new Thread(new FakeClient());
      Thread fc2 = new Thread(new FakeClient());
      fc1.start();
      fc2.start();
      for(int i = 0; i<2; i++){
        SocketChannel sc = server.accept();
        int n = server.getPlayerNum(sc);
        GameMaster gm = server.getGameFor(n);
        gm.addPlayer(sc);
        if(gm.isFull()){
          Thread gameMaster = new Thread(gm);
          //gameMaster.start();
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
