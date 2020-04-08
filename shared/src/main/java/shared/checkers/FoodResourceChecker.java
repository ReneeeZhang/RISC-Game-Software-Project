package shared.checkers;

import shared.Board;
import shared.Player;
import shared.Region;

public class FoodResourceChecker implements Checker {
  Board board;
  Region source;
  Region dest;
  int num;
  Checker next;
  
  public FoodResourceChecker(Board board, Region source, Region dest, int num) {
    this(board, source, dest, num, null);
  }
  
  public FoodResourceChecker(Board board, Region source, Region dest, int num, Checker next) {
    this.board = board;
    this.source = source;
    this.dest = dest;
    this.num = num;
    this.next = next;
  }
  
  @Override
  public boolean isValid() {
    Player player = board.getPlayer(source.getOwner());
    int distance = board.getDistance(source.getName(), dest.getName());
    int food = player.getFoodAmount();
    if (food < distance * num) {
      System.out.println(String.format("Move failed because of lacking food. Source: %s, Destination: %s. " + "Having: %d, Expected: %d", source.getName(), dest.getName(), food, distance));
      return false;
    }
    return next == null || next.isValid();
  }
}
