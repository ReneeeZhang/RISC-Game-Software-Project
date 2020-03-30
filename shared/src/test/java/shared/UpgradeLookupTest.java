package shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import shared.*;

public class UpgradeLookupTest {
  @Test
  public void test_() {
    UpgradeLookup u = new UpgradeLookup();
    assertEquals(0, u.getCostTech(1));
    assertEquals(200, u.getCostTech(5));
    assertEquals(8, u.getCostUnit(1, 2));
    assertEquals(15, u.getBonus(6));
    assertEquals(false, u.validTechLevel(0));
    assertEquals(false, u.validTechLevel(10));
    assertEquals(true, u.validTechLevel(3));
    assertEquals(false, u.validUnitLevel(-1));
    assertEquals(true, u.validUnitLevel(5));
  }

}
