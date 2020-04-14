package shared.instructions;

import shared.Board;
import shared.Region;
import shared.checkers.AdjacentChecker;
import shared.checkers.UnitQuantityChecker;

public class Attack extends R2RInstruction {
  private static final long serialVersionUID = 923749346;
  
  public Attack(String p, String s, String d, int l, int n) {
    super(p, s, d, l, n);
  }

  @Override
  public void execute(Board b) {
    //TODO: add player as arg
    b.attack(src, dest, level, numUnit);
  }

  @Override
  public boolean isValid(Board b) {
    Region source =  b.getRegion(src);
    Region destination = b.getRegion(dest);
    UnitQuantityChecker uChecker = new UnitQuantityChecker(source, level, numUnit);
    AdjacentChecker aChecker = new AdjacentChecker(b, source, destination, uChecker);
    boolean sameOwner = source.getOwner().equals(destination.getOwner());
    return aChecker.isValid() && !sameOwner;
  }
}
