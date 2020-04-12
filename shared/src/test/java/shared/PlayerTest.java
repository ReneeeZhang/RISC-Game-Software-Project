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

}
