package shared.checkers;

import shared.Board;
import shared.Region;

public class GameOverChecker implements Checker {

    Board board;
    Checker next;

    public GameOverChecker(Board board) {
        this (board, null);
    }

    public GameOverChecker(Board board, Checker next) {
        this.board = board;
        this.next = next;
    }

    @Override
    public boolean isValid() {
        //TODO, type of owner
        String owner = null;
        for (Region region : board.getAllRegions()) {
            if (owner == null) {
                owner = region.getOwner();
                continue;
            }
            if (!region.getOwner().equals(owner)) {
                return false;
            }
        }
        return next == null || next.isValid();
    }
}
