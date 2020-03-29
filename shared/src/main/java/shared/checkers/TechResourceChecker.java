package shared.checkers;

import shared.Board;
import shared.Player;
import shared.Region;

public class TechResourceChecker implements Checker {
    Board board;
    Region source;
    Region dest;
    Checker next;

    public TechResourceChecker(Board board, Region source, Region dest) {
        this(board, source, dest, null);
    }

    public TechResourceChecker(Board board, Region source, Region dest, Checker next) {
        this.board = board;
        this.source = source;
        this.dest = dest;
        this.next = next;
    }

    @Override
    public boolean isValid() {
        Player player = board.getPlayer(source.getOwner());
        int distance = board.getDistance(source.getName(), dest.getName());
        if (player.getFood() < distance) {
            return false;
        }
        return next == null || next.isValid();
    }
}
