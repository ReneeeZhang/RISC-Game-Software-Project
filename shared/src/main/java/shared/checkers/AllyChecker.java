package shared.checkers;

import shared.Board;
import shared.Player;

public class AllyChecker implements Checker {
  private Board b;
  private Player player1;
  private Player player2;
  private Checker next;

  public AllyChecker(Board b, String player1, String player2) {
    this(b, player1, player2, null);
  }

  public AllyChecker(Board b, String player1, String player2, Checker next) {
    this.b = b;
    this.player1 = b.getPlayer(player1);
    this.player2 = b.getPlayer(player2);
    this.next = next;
  }

  @Override
  public boolean isValid() {
    boolean canAlly = player1.getAlly() == null && player2.getAlly() == null;
    return next == null ? canAlly : canAlly && next.isValid();
  }
}
