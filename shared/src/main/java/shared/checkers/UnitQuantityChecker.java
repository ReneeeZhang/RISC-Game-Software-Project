package shared.checkers;

import shared.Region;

public class UnitQuantityChecker implements Checker {

    private Region region;
    private int expect;
    private Checker next;

    public UnitQuantityChecker(Region region, int expect) {
        this(region, expect, null);
    }

    public UnitQuantityChecker(Region region, int expect, Checker next) {
        this.region = region;
        this.expect = expect;
        this.next = next;
    }

    @Override
    public boolean isValid() {
        boolean valid = region.getNumBaseUnit() >= expect;
        return next == null ? valid : valid && next.isValid();
    }
}
