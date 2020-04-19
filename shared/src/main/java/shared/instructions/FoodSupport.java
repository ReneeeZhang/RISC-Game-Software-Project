package shared.instructions;

import shared.Board;
import shared.checkers.CorrectAllyChecker;
import shared.checkers.FoodAmountChecker;

public class FoodSupport extends P2PInstruction {
  private static final long serialVersionUID = 2435234;
  private int amount;
  
  public FoodSupport(String supportor, String supportee, int amount) {
    super(supportor, supportee);
    this.amount = amount;
  }

  @Override
  public boolean isValid(Board board) {
    FoodAmountChecker faChecker = new FoodAmountChecker(board.getPlayer(player1), amount, null);
    CorrectAllyChecker caChecker = new CorrectAllyChecker(board.getPlayer(player1), board.getPlayer(player2),
        faChecker);
    return caChecker.isValid();
  }

  @Override
  public void execute(Board board) {
    board.supportFood(player1, player2, amount); 
  }
}
