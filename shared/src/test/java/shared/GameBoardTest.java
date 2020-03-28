package shared;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GameBoardTest {
  @Test
  public void test_GameBoard() {
    Board b1 = new GameBoard();
    Board b2 = new GameBoard(new HashMap<Region, List<Region>>());
    Initializer init = new Initializer(5);
    try{
      Board board = init.initGame();
      board.getAllOwners();
      board.getAllRegionNames();
      board.getAllRegions();
      board.getNeighbor("Hudson");
      board.getRegion("Hudson");
    } catch (IOException e) {
      System.out.println(e);
    }
  }

}
