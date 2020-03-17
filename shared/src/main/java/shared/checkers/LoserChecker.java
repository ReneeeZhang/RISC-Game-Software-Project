package shared.checkers;

import shared.Board;

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
        //TODO, needs getRegions() in board

        return false;
    }
}
