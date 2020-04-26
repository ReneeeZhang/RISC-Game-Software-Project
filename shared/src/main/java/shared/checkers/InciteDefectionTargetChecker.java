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
    if (inciter.equals(incitee)) {
      System.out.println(inciter.getName() + " is trying to incite from his owner region");
      return false;
    }
    else if (inciter.getAlly() == null) {
      return true;
    }
    else {
      return !inciter.getAlly().equals(incitee);
    }
  }
}
