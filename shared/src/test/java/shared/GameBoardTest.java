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
      board.getRegionNames("Player1");
      board.getAllRegions();
      board.getNeighbor("Hudson");
      board.getRegion("Hudson");
      board.getDistance("Hudson", "Fitzpatrick");
      board.move("Hudson", "Fitzpatrick", 0, 1);
      board.attack("Fitzpatrick", "Wilson", 0, 3);
      board.resolve();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

}
