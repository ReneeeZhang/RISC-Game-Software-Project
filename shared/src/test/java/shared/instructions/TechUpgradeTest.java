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
    TechUpgrade t = new TechUpgrade("Drew", 1, 2);

    Board b = mock(Board.class);
    when(b.getPlayer("Drew")).thenReturn(p);
    
    t.execute(b);
    assertEquals(true, t.isValid(b));
    assertEquals(2, p.getLevel());
  }

}
