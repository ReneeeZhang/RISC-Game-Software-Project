package shared.checkers;

import shared.Board;

public class UnitUpgradeChecker implements Checker {

    Board board;
    Checker next;

    public UnitUpgradeChecker(Board board, Checker next) {
        this.board = board;
        this.next = next;
    }

    public UnitUpgradeChecker(Board board) {
        this(board, null);
    }

    @Override
    public boolean isValid() {

        return false;
    }
}
