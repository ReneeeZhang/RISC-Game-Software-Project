package shared.checkers;

import shared.Board;
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
        //TODO, getOwner()

        return false;
    }
}
