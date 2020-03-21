package shared.checkers;

import shared.*;

public class R2RInstructionChecker implements Checker {

    Board board;
    R2RInstruction instruction;

    public R2RInstructionChecker(Board board, R2RInstruction instruction) {
        this.board = board;
        this.instruction = instruction;
    }

    @Override
    public boolean isValid() {
        Region src = instruction.getSrc();
        Region dest = instruction.getDest();
        int units = instruction.getNumUnit();
        UnitQuantityChecker unitQuantityChecker = new UnitQuantityChecker(src, units);
        if (instruction instanceof Move) {
            unitQuantityChecker.setNext(new AccessibleChecker(board, src, dest));
        } else if (instruction instanceof Attack) {
            if (src.getOwner().equals(dest.getOwner())) {
                return false;
            }
            unitQuantityChecker.setNext(new AdjacentChecker(board, src, dest));
        }
        return unitQuantityChecker.isValid();
    }
}
