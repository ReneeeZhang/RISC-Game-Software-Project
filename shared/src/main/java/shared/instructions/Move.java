package shared.instructions;

import shared.*;
import shared.checkers.*;

public class Move extends R2RInstruction {
  private static final long serialVersionUID = 923749345;

  public Move(String p, String s, String d, int l, int n) {
    super(p, s, d, l, n);
  }

  @Override
  public void execute(Board b) {
    //TODO: add player as arg
    b.move(src, dest, level, numUnit);
  }

  @Override
  public boolean isValid(Board b) {
    Region source = b.getRegion(src);
    Region destination = b.getRegion(dest);
    FoodResourceChecker fChecker = new FoodResourceChecker(b, source, destination, numUnit);
    AccessibleChecker aChecker = new AccessibleChecker(b, source, destination, fChecker);
    UnitQuantityChecker uChecker = new UnitQuantityChecker(source, level, numUnit, aChecker);
    return uChecker.isValid();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Move: move ").append(numUnit).append(" unit(s) from ").append(src).append(" to ").append(dest)
        .append(".\n");
    return sb.toString();
  }
}
