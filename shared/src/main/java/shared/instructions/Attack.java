package shared.instructions;

import java.io.Serializable;
import shared.*;
import shared.checkers.*;

public class Attack extends R2RInstruction implements Serializable{
  private static final long serialVersionUID = 923749346;
  
  public Attack(String s, String d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    b.attack(src, dest, numUnit);
  }

  @Override
  public boolean isValid(Board b) {
    Region source =  b.getRegion(src);
    Region destination = b.getRegion(dest);
    AdjacentChecker checker = new AdjacentChecker(b, source, destination);
    UnitQuantityChecker uChecker = new UnitQuantityChecker(source, numUnit);
    boolean sameOwner = source.getOwner().equals(destination.getOwner());
    return checker.isValid() && uChecker.isValid() && !sameOwner;
  }
}
