package shared.checkers;

import shared.Player;

public class CorrectAllyChecker implements Checker {
  private Player player;
  private Player expectedAlly;
  private Checker next;

  public CorrectAllyChecker(Player player, Player expectedAlly) {
    this(player, expectedAlly, null);
  }

  public CorrectAllyChecker(Player player, Player expectedAlly, Checker next) {
    this.player = player;
    this.expectedAlly = expectedAlly;
    this.next = next;
  }

  @Override
  public boolean isValid() {
    boolean isCorrectAlly = player.getAlly().equals(expectedAlly);
    return next == null ? isCorrectAlly : isCorrectAlly && next.isValid();
  }
}
