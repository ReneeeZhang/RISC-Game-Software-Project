package shared;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class GameBoardTest {
  @Test
  public void test_GameBoard() {
    Board b = new GameBoard();
    Initializer init = new Initializer(2);
    try{
      Board board = init.initGame();
      board.getPlayer("Player1");
      board.getAllOwners();
      board.getAllRegionNames();
      board.getAllRegions();
      board.getNeighbor("Hudson");
      board.getRegion("Hudson");
      board.getDistance("Hudson", "Fitzpatrick");
      board.move("Hudson", "Fitzpatrick", 1, 1);
      board.attack("Fitzpatrick", "Wilson", 1, 3);
      board.resolve();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

}
