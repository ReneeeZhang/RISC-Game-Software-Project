package shared.checkers;

import shared.Player;

public class FoodAmountChecker implements Checker {
  private Player player;
  private int amount;
  private Checker next;
  
  public FoodAmountChecker(Player player, int amount) {
    this(player, amount, null);
  }

  public FoodAmountChecker(Player player, int amount, Checker next) {
    this.player = player;
    this.amount = amount;
    this.next = next;
  }

  @Override
  public boolean isValid() {
    boolean hasEnoughFood = player.getFoodAmount() >= amount;
    return next == null ? hasEnoughFood : hasEnoughFood && next.isValid();
  }
}
