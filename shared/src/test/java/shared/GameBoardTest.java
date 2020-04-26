package shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameBoardTest {
  private Board board;

  @BeforeEach
  public void init() {
    Initializer init = new Initializer(5);
    try{
      this.board = init.initGame();
    }
    catch (IOException e) {
    }
  }

  @Test
  public void test_GameBoard() {
    board.getPlayer("Player1");
    board.getAllOwners();
    board.getAllRegionNames();
    board.getRegionNames("Player1");
    board.getAllRegions();
    board.getAllRegions("Player1");
    board.getNeighbor("Hudson");
    board.getRegion("Hudson");
    int dist = board.getDistance("Player1", "Hudson", "Wilson");
    System.out.println("Shortest distance between hudson and wilson: " + dist);
    board.move("Player1", "Hudson", "Fitzpatrick", 0, 1);
    board.attack("Player1", "Fitzpatrick", "Wilson", 0, 3);
    board.inciteDefection("Hudson", "Fitzpatrick");
    board.resolve();
  }

  @Test
  public void test_ally() {
    board.ally("Player1", "Player2");
    assertEquals(board.getPlayer("Player1").getAlly(), board.getPlayer("Player2"));
  }

  @Test
  public void test_resolveAlly1() {
    board.ally("Player1", "Player2");
    board.resolveAlly();
    assertNull(board.getPlayer("Player1").getAlly());
  }

  @Test
  public void test_resolveAlly2() {
    board.ally("Player1", "Player2");
    board.ally("Player2", "Player1");
    board.resolveAlly();
    assertEquals(board.getPlayer("Player1").getAlly(), board.getPlayer("Player2"));
  }
  
  @Test
  public void test_suppportFood() {
    Player player1 = board.getPlayer("Player1");
    Player player2 = board.getPlayer("Player2");
    player1.allyWith(player2);
    player2.allyWith(player2);
    board.supportFood("Player1", "Player2", 5);
    assertEquals(player1.getFoodAmount(), 45);
    assertEquals(player2.getFoodAmount(), 55);
  }

  @Test
  public void test_toString() {
    board.toString();
  }
}
