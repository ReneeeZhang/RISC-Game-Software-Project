package shared.instructions;

import java.io.Serializable;
import shared.*;
import shared.checkers.*;

public class Move extends R2RInstruction implements Serializable {
  private static final long serialVersionUID = 923749345;
  
  public Move(String s, String d, int l, int n) {
    super(s, d, l, n);
  }

  @Override
  public void execute(Board b) {
    b.move(src, dest, level, numUnit);
  }

  @Override
  public boolean isValid(Board b) {
    Region source = b.getRegion(src);
    Region destination = b.getRegion(dest);
    AccessibleChecker aChecker = new AccessibleChecker(b, source, destination);
    UnitQuantityChecker uChecker = new UnitQuantityChecker(source, level, numUnit, aChecker);
    FoodResourceChecker fChecker = new FoodResourceChecker(b, source, destination, numUnit, uChecker);
    return fChecker.isValid();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Move: move ").append(numUnit).append(" unit(s) from ").append(src).append(" to ").append(dest)
        .append(".\n");
    return sb.toString();
  }
  
}




