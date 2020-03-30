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

}
