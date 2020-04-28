
package shared.checkers;

import shared.Player;
import shared.Region;

public class UnitQuantityChecker implements Checker {
  private Region region;
  private Player whoOwns;
  private int level;
  private int expect;
  private Checker next;
  
  public UnitQuantityChecker(Region region, Player whoOwns, int level, int expect) {
    this(region, whoOwns, level, expect, null);
  }
  
  public UnitQuantityChecker(Region region, Player whoOwns, int level, int expect, Checker next) {
    this.region = region;
    this.whoOwns = whoOwns;
    this.level = level;
    this.expect = expect;
    this.next = next;
  }
  
  @Override
  public boolean isValid() {
    boolean valid = region.numUnitWithLevel(whoOwns, level) >= expect;
    if (!valid) {
      System.out.println("Instruction failed because units are not abundant. Source: " + region.getName());
      return false;
    }
    return next == null || next.isValid();
  }
}
