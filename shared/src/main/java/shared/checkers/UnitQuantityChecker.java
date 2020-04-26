package shared.checkers;

import shared.Region;

public class UnitQuantityChecker implements Checker {
  private Region region;
  private int level;
  private int expect;
  private Checker next;
  
  public UnitQuantityChecker(Region region, int level, int expect) {
    this(region, level, expect, null);
  }
  
  public UnitQuantityChecker(Region region, int level, int expect, Checker next) {
    this.region = region;
    this.level = level;
    this.expect = expect;
    this.next = next;
  }
  
  @Override
  public boolean isValid() {
    boolean valid = region.numUnitWithLevel(level) >= expect;
    if (!valid) {
      System.out.println("Instruction failed because units are not abundant. Source: " + region.getName());
      return false;
    }
    return next == null || next.isValid();
  }
}
