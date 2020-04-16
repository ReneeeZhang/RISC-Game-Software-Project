package shared.instructions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import shared.*;

public class UnitUpgradeTest {
  @Test
  public void test_() {
    Player p = new Player("Drew");
    p.increaseTech(100);
    UnitUpgrade u = new UnitUpgrade("Drew", "Hudson", 0, 1, 1);
    assertEquals(1, u.getNumUnit());
    assertEquals(1, u.getNewLevel());

    Board b = mock(Board.class);
    when(b.getPlayer("Drew")).thenReturn(p);
    Region r = new BaseRegion("Hudson", p, 10);
    when(b.getRegion("Hudson")).thenReturn(r);

    assertEquals("Hudson", u.getSrc());
    assertEquals(true, u.isValid(b));
    u.execute(b);
    
  }

}
