package shared.checkers;

import shared.Board;
import shared.Region;

import java.util.List;

public class WinnerChecker implements Checker {
    Checker next;
    Board board;
    String owner;

    public WinnerChecker(Board board, String owner) {
        this(board, owner, null);
    }

    public WinnerChecker(Board board, String owner, Checker next) {
        this.next = next;
        this.board = board;
        this.owner = owner;
    }

    @Override
    public boolean isValid() {
        List<Region> allRegions = board.getAllRegions();
        for (Region region : allRegions) {
            if (!region.getOwner().equals(owner)) return false;
        }
        return next == null || next.isValid();
    }
}
