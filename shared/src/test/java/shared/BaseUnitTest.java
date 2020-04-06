package shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BaseUnitTest {
  @Test
  public void test_baseUnit() {
    BaseUnit bu = new BaseUnit();
    bu.upgrade();
    assertTrue(bu.getCurrLevel() == 1);
    bu.upgradeTo(3);
    assertTrue(bu.getCurrLevel() == 3);
  }

  @Test
  public void test_compareTo() {
    BaseUnit b1 = new BaseUnit();
    BaseUnit b2 = new BaseUnit();
    BaseUnit b3 = new BaseUnit();
    b2.upgrade();
    b3.upgradeTo(2);
    assertTrue(b1.compareTo(b3) == -1);
    assertTrue(b1.compareTo(b1) == 0);
    assertTrue(b3.compareTo(b1) == 1);
  }
}
