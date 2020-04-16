package shared.checkers;

import shared.Board;
import shared.Player;

public class FoodResourceChecker implements Checker {
  Board board;
  String player;
  String source;
  String dest;
  int num;
  Checker next;
  
  public FoodResourceChecker(Board board, String player, String source, String dest, int num) {
    this(board, player, source, dest, num, null);
  }
  
  public FoodResourceChecker(Board board, String player, String source, String dest, int num, Checker next) {
    this.board = board;
    this.player = player;
    this.source = source;
    this.dest = dest;
    this.num = num;
    this.next = next;
  }
  
  @Override
  public boolean isValid() {
    int distance = board.getDistance(player, source, dest);
    Player p = board.getPlayer(player);
    int food = p.getFoodAmount();
    if (food < distance * num) {
      System.out.println(String.format("Move failed because of lacking food. Source: %s, Destination: %s. " + "Having: %d, Expected: %d", source, dest, food, distance));
      return false;
    }
    return next == null || next.isValid();
  }
}
