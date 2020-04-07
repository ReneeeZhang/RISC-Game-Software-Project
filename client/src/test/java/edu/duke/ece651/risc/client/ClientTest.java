package edu.duke.ece651.risc.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import shared.Board;
import shared.instructions.Move;

public class ClientTest {
  @Test
  public void test_client() {
    System.out.println("Test client");
    Thread serverThread = new Thread(new FakeServer(6666));
    serverThread.start();
    Thread serverThread2 = new Thread(new FakeServer2(7777));
    serverThread2.start();
    
    try {
      Client client = new Client("localhost", 6666, 7777);
      client.send(2);
      String name = (String) client.receive();
      Board board = (Board) client.receive();

      client.joinGame();
      client.initMatch(0, name, board);
      client.getBoard(0);
      client.setBoard(0, board);
      client.hasLost(0);
      client.hasWon(0);
      client.isGameOver(0);

      client.sendViaChannel(0, 1);
      String a = (String) client.receiveViaChannel(0);
      Move m1 = new Move("Hudson", "Teer", 4, 1);
      Move m2 = new Move("Hudson", "Teer", 0, 1);
      client.isValidInst(0, m1);
      client.isValidInst(0, m2);
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    try{
      Thread.sleep(50);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
    //Client.main(new String[] { "localhost", "6666" });
  }
}
