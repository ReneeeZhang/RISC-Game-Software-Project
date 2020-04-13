package shared.instructions;

import shared.Board;
import shared.checkers.Checker;
import shared.checkers.AllyChecker;

public class Ally extends P2PInstruction {
  private static final long serialVersionUID = 5834901;

  public Ally(String player1, String player2) {
    super(player1, player2);
  }

  public boolean isValid(Board b) {
    Checker aChecker = new AllyChecker(b, player1, player2);
    return aChecker.isValid();
  }

  public void execute(Board b) {
    b.ally(player1, player2);
  }
}
