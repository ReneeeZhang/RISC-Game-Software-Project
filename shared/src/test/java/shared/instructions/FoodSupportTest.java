package shared.instructions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import shared.Board;
import shared.Player;

public class FoodSupportTest {
  @Test
  public void test_isValid() {
    Board b = mock(Board.class);
    Player p1 = new Player("p1");
    Player p2 = new Player("p2");
    p1.allyWith(p2);
    p2.allyWith(p1);
    when(b.getPlayer("p1")).thenReturn(p1);
    when(b.getPlayer("p2")).thenReturn(p2);
    FoodSupport fs = new FoodSupport("p1", "p2", 5);
    assertTrue(fs.isValid(b));
    fs.execute(b);
  }
  

}
