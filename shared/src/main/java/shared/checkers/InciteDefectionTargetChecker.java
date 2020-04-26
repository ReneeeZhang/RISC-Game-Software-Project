package shared.checkers;

import shared.Player;

public class InciteDefectionTargetChecker implements Checker {
  private Player inciter;
  private Player incitee;
  private Checker next;

  public InciteDefectionTargetChecker(Player inciter, Player incitee) {
    this(inciter, incitee, null);
  }

  public InciteDefectionTargetChecker(Player inciter, Player incitee, Checker next) {
    this.inciter = inciter;
    this.incitee = incitee;
    this.next = next;
  }

  public boolean isValid() {
    boolean correctTarget = false;
    if (inciter.equals(incitee)) {
      System.out.println(inciter.getName() + " is trying to incite from his owner region");
      correctTarget = false;
    }
    else if (inciter.getAlly() == null) {
      correctTarget = true;
    }
    else {
      correctTarget = !inciter.getAlly().equals(incitee);
    }
    return next == null ? correctTarget : correctTarget && next.isValid();
  }
}
