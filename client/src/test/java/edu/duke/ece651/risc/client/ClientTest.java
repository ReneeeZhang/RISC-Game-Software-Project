package edu.duke.ece651.risc.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import shared.Board;
import shared.instructions.Move;

public class ClientTest {
  @Test
  public void test_client() {
    System.out.println("Test client");
    Thread serverThread = new Thread(new FakeServer(6666));
    serverThread.start();
    Thread gameServerThread = new Thread(new FakeGameServer(7777));
    gameServerThread.start();
    Thread chatServerThread = new Thread(new FakeChatServerForClient(9999));
    chatServerThread.start();
    
    try {
      Client client = new Client("localhost", 6666, 7777, 9999);
      client.send(2);
      String name = (String) client.receive();
      Board board = (Board) client.receive();

      client.joinGame();
      client.sendNumPlayer(0, 2);
      
      client.initMatch(0, name, board);
      client.getBoard(0);
      client.setBoard(0, board);
      client.hasLost(0);
      client.hasWon(0);
      client.isGameOver(0);


      //Client.main(new String[] { "localhost", "6666" });


      client.sendViaChannel(0, 1);
      String a = (String) client.receiveViaChannel(0);
      Move m1 = new Move("p1", "Hudson", "Teer", 4, 1);
      Move m2 = new Move("p2", "Perkins", "Teer", 0, 1);
      client.isValidInst(0, m1);
      //client.isValidInst(0, m2);

      //send and receive msg
      client.sendChatMsg(0, "chat msg");
      String msg = client.receiveChatMsg(0);
      assertEquals(msg, "You: chat msg");
      Thread.sleep(1000);
    } catch (Exception e) {
    }
    //Client.main(new String[] { "localhost", "6666" });
  }

}
