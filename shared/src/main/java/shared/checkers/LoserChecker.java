package shared.checkers;

import shared.Board;
import shared.Region;

import java.util.List;

public class LoserChecker implements  Checker {

    private Board board;
    private String owner;
    private Checker next;

    public LoserChecker(Board board, String owner) {
        this(board, owner, null);
    }

    public LoserChecker(Board board, String owner, Checker next) {
        this.board = board;
        this.owner = owner;
        this.next = next;
    }

    @Override
    public boolean isValid() {
        List<Region> allRegions = board.getAllRegions();
        for (Region region : allRegions) {
          if (region.getOwner().getName().equals(owner)) return false;
        }
        return next == null || next.isValid();
    }
}
