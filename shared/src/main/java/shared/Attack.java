package shared;

import shared.checkers.*;

public class Attack extends R2RInstruction {
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
    Checker c = new AdjacentChecker(board, board.getRegion(src), board.getRegion(dest));
    return c.isValid();
  }
}
