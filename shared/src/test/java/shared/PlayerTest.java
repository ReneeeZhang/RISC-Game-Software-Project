package shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlayerTest {
  @Test
  public void test_getName() {
    Player p = new Player("a");
    assertTrue(p.getName().equals("a"));
  }

  @Test
  public void test_getFoodAmount() {
    Player p = new Player("a");
    assertTrue(p.getFoodAmount() == 50);
  }

  @Test
  public void test_ally() {
    Player p = new Player("p");
    Player q = new Player("q");
    p.allyWith(q);
    assertTrue(p.getAlly().getName().equals("q"));
    p.breakAlly();
    assertTrue(p.getAlly() == null);
  }
  
  @Test
  public void test_decreaseFood() {
    Player p = new Player("a");
    p.increaseFood(4);
    p.decreaseFood(2);
    assertTrue(p.getFoodAmount() == 52);
  }

  @Test
  public void test_upgradeTo() {
    Player p = new Player("a");
    assertThrows(NoSuchMethodException.class, () -> {
      p.upgradeTo(2);
    });
  }

  @Test
  public void test_equals() {
    Player p = new Player("a");
    Player q = new Player("b");
    Object o = new Object();
    assertTrue(p.equals(p));
    assertFalse(p.equals(q));
    assertFalse(p.equals(o));
  }
}
