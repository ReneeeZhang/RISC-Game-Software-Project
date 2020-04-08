package shared.instructions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import shared.*;

public class TechUpgradeTest {
  @Test
  public void test_() {
    
    Player p = new Player("Drew");
    p.increaseTech(100);
    assertEquals(150, p.getTechAmount());
    
    TechUpgrade t = new TechUpgrade("Drew", 1, 2);
    assertEquals(1, t.getOldLevel());

    Board b = mock(Board.class);
    when(b.getPlayer("Drew")).thenReturn(p);

    assertEquals(true, t.isValid(b));
    t.execute(b);
    assertEquals(2, p.getCurrLevel());
  }

}
