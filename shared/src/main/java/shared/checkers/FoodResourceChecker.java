package shared.checkers;

import shared.Board;
import shared.Player;
import shared.Region;

public class FoodResourceChecker implements Checker {
    Board board;
    Region source;
    Region dest;
    Checker next;

    public FoodResourceChecker(Board board, Region source, Region dest) {
        this(board, source, dest, null);
    }

    public FoodResourceChecker(Board board, Region source, Region dest, Checker next) {
        this.board = board;
        this.source = source;
        this.dest = dest;
        this.next = next;
    }

    @Override
    public boolean isValid() {
        Player player = board.getPlayer(source.getOwner());
        int distance = board.getDistance(source.getName(), dest.getName());
        int food = player.getFoodAmount();
        if (food < distance) {
            System.out.println(String.format("Move failed because of lacking food. Source: %s, Destination: %s. " +
                    "Having: %d, Expected: %d", source.getName(), dest.getName(), food, distance));
            return false;
        }
        return next == null || next.isValid();
    }
}
