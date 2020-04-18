package shared.checkers;

import shared.Board;
import shared.Player;

public class AllyChecker implements Checker {
  private Board b;
  private String player1;
  private String player2;
  private Checker next;

  public AllyChecker(Board b, String player1, String player2) {
    this(b, player1, player2, null);
  }

  public AllyChecker(Board b, String player1, String player2, Checker next) {
    this.b = b;
    this.player1 = player1;
    this.player2 = player2;
    this.next = next;
  }

  @Override
  public boolean isValid() {
    Player p1 = b.getPlayer(player1);
    Player p2 = b.getPlayer(player2);
    boolean canAlly = p1.getAlly() == null && p2.getAlly() == null;
    return next == null ? canAlly : canAlly && next.isValid();
  }
}
