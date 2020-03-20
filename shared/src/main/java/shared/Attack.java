package shared;
import java.io.Serializable;
import shared.checkers.*;

public class Attack extends R2RInstruction implements Serializable{
  private static final long serialVersionUID = 923749346;
  
  public Attack(String s, String d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    this.board = b;
    if(isValid()) {
      //b.attack(src, dest, numUnit)
    }
  }

  @Override
  public boolean isValid() {
    Region r1 = board.getRegion(src);
    Region r2 = board.getRegion(dest);
    Checker c = new AdjacentChecker(board, r1, r2);
    boolean sameOwner = r1.getOwner().equals(r2.getOwner());
    return c.isValid() && !sameOwner;
  }
}
