package shared.checkers;

import shared.Board;
import shared.Region;

import java.util.List;

public class AdjacentChecker implements Checker {
    private Board board;
    private Region source;
    private Region dest;
    private Checker next;

    public AdjacentChecker(Board board, Region source, Region dest) {
        this(board, source, dest, null);
    }

    public AdjacentChecker(Board board, Region source, Region dest, Checker next) {
        this.board = board;
        this.source = source;
        this.dest = dest;
        this.next = next;
    }
    public void setNext(Checker next) {
        this.next = next;
    }
    @Override
    public boolean isValid() {
        List<Region> neighbor = board.getNeighbor(source.getName());
        boolean contains = neighbor.contains(dest);
        return next == null ? contains : contains && next.isValid();
    }
}
