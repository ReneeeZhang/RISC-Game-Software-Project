package shared;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GameBoardTest {
  @Test
  public void test_GameBoard() {
    Board b1 = new GameBoard();
    Board b2 = new GameBoard(new HashMap<Region, List<Region>>());
  }

}
