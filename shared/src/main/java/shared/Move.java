package shared;

import shared.checkers.*;

public class Move extends R2RInstruction {
  public Move(String s, String d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    this.board = b;
    if(isValid()) {
      b.move(src, dest);
    }
  }

  @Override
  public boolean isValid() {
    Checker c = new AccessibleChecker(board, board.getRegion(src), board.getRegion(dest));
    return c.isValid();
  }
}
