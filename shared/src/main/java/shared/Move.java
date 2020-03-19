package shared;

import java.io.Serializable;

import shared.checkers.AccessibleChecker;
import shared.checkers.Checker;

public class Move extends R2RInstruction implements Serializable {
  private static final long serialVersionUID = 923749345;
  
  public Move(String s, String d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    this.board = b;
    if(isValid()) {
      b.move(src, dest, numUnit);
    }
  }

  @Override
  public boolean isValid() {
    Checker c = new AccessibleChecker(board, board.getRegion(src), board.getRegion(dest));
    return c.isValid();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Move: move ").append(numUnit).append(" unit(s) from ").append(src).append(" to ").append(dest)
        .append(".\n");
    return sb.toString();
  }
  
}
