package shared.checkers;

import shared.Board;

public class WinnerChecker implements Checker {
    Checker next;
    Board board;
    String owner;

    public WinnerChecker(Board board, String owner) {
        this(null, board, owner);
    }

    public WinnerChecker(Checker next, Board board, String owner) {
        this.next = next;
        this.board = board;
        this.owner = owner;
    }

    @Override
    public boolean isValid() {
        //TODO, needs getRegions() in board
        return false;
    }
}
