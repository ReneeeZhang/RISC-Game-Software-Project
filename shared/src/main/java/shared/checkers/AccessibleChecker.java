package shared.checkers;

import shared.Board;
import shared.Region;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class AccessibleChecker implements Checker {
    private Board board;
    private Region source;
    private Region dest;
    private Checker next;
    //record visited region
    private Set<Region> visited = new HashSet<>();

    public AccessibleChecker(Board board, Region source, Region dest) {
        this(board, source, dest, null);
    }

    public AccessibleChecker(Board board, Region source, Region dest, Checker next) {
        this.board = board;
        this.source = source;
        this.dest = dest;
        this.next = next;
    }
    /**
     * helper function -- check if two regions belong to same owner
     */
    private boolean isSameOwner(Region r1, Region r2) {
        return r1.getOwner().equals(r2.getOwner());
    }

    /**
     * helper function -- recursively find destination region among neighbours
     * @param r -- a Region
     * @return destination is accessible for a region or not
     */
    private boolean isAccessible(Region r) {
        visited.add(r);
        List<Region> neighbor = board.getNeighbor(r.getName());
        if (neighbor.contains(dest)) return true;

        for (Region region : neighbor) {
            if (visited.contains(region) || !isSameOwner(region, r)) continue;
            if (isAccessible(region)) return true;
        }

        return false;
    }
    @Override
    public boolean isValid() {
        if (!isSameOwner(source, dest)) return false;
        boolean accessible = isAccessible(source);
        return next == null ? accessible : accessible && next.isValid();
    }

}
