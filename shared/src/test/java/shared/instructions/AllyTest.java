package shared.instructions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shared.Board;
import shared.Initializer;

public class AllyTest {
  private Ally ally;
  private Board b;
  
  @BeforeEach
  public void setUp() {
    ally = new Ally("Player1", "Player2");
    Initializer init = new Initializer(3);
    try{
      b = init.initGame();
    } catch (Exception e) {
    }
  }
  
  @Test
  public void test_isValid() {
    ally.isValid(b);
  }

  @Test
  public void test_execute() {
    ally.execute(b);
  }
  
}
