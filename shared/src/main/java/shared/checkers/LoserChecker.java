package shared.checkers;

import shared.Board;
import shared.Region;

import java.util.List;

public class LoserChecker implements  Checker {

    Checker next;
    Board board;
    String owner;

    public LoserChecker(Board board, String owner) {
        this(null, board, owner);
    }

    public LoserChecker(Checker next, Board board, String owner) {
        this.next = next;
        this.board = board;
        this.owner = owner;
    }

    @Override
    public boolean isValid() {
        List<Region> allRegions = board.getAllRegions();
        for (Region region : allRegions) {
            if (region.getOwner().equals(owner)) return false;
        }
        return next == null || next.isValid();
    }
}
