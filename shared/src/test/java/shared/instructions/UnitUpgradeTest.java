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

    Board b = mock(Board.class);
    when(b.getPlayer("Drew")).thenReturn(p);

    assertEquals("Hudson", u.getSrc());
    u.execute(b);
    assertEquals(true, u.isValid(b));
  }

}
