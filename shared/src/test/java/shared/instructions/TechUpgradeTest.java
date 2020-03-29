package shared.instructions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import shared.*;

public class TechUpgradeTest {
  @Test
  public void test_() {
    GameBoard b = new GameBoard();
    Player p = new Player("Drew");
    p.increaseTech(100);
    TechUpgrade t = new TechUpgrade(1, 2);
    t.execute(b);
    assertEquals(true, t.isValid(b));
    t.execute(p);
    assertEquals(true, t.isValid(p));
    assertEquals(2, p.getLevel());
  }

}
