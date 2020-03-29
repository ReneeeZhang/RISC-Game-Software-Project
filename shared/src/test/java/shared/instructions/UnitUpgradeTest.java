package shared.instructions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import shared.*;

public class UnitUpgradeTest {
  @Test
  public void test_() {
    Board b = new GameBoard();
    Player p = new Player("Drew");
    p.increaseTech(100);
    UnitUpgrade u = new UnitUpgrade("Hudson", 0, 1, 1);
    u.execute(b);
    assertEquals(true, u.isValid(b));

    assertEquals("Hudson", u.getSrc());
    u.execute(b, p);
    assertEquals(true, u.isValid(b, p));
  }

}
