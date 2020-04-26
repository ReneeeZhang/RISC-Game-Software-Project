package shared.instructions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shared.Board;
import shared.Initializer;

public class InciteDefectionTest {
  private Board board;

  @BeforeEach
  public void init() {
    try{
      Initializer init = new Initializer(2);
      this.board = init.initGame();
    } catch (IOException ex) {
    }
  }
  
  @Test
  public void test_isValid() {
    InciteDefection id = new InciteDefection(null, "Perkins", "Fitzpatrick", 0, 0);
    assertTrue(id.isValid(board));
  }
}
