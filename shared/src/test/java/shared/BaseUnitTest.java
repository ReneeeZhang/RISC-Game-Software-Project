package shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BaseUnitTest {
  @Test
  public void test_baseUnit() {
    Player p = new Player("p");
    BaseUnit bu = new BaseUnit(p);
    bu.upgrade();
    assertTrue(bu.getCurrLevel() == 1);
    bu.upgradeTo(3);
    assertTrue(bu.getCurrLevel() == 3);
  }

  @Test
  public void test_compareTo() {
    Player p = new Player("p");
    BaseUnit b1 = new BaseUnit(p);
    BaseUnit b2 = new BaseUnit(p);
    BaseUnit b3 = new BaseUnit(p);
    b2.upgrade();
    b3.upgradeTo(2);
    assertTrue(b1.compareTo(b3) == -1);
    assertTrue(b1.compareTo(b1) == 0);
    assertTrue(b3.compareTo(b1) == 1);
  }

  @Test
  public void test_getOwner() {
    Player p = new Player("p");
    BaseUnit b = new BaseUnit(p);
    assertTrue(b.getOwner().equals(p));
  }
}
