package shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UnitNameLookupTest {
  @Test
  public void test_() {
    UnitNameLookup table = new UnitNameLookup();
    assertEquals("freshman", table.getName(0));
  }

}
