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
        Region source = board.getRegion(instruction.getSrc());
        Region dest = board.getRegion(instruction.getDest());
        if (source == null || dest == null) {
            System.out.println("Instruction failed because board does not contain a region named: " + (source != null ? instruction.getDest() : instruction.getSrc()));
            return false;
        }
        int units = instruction.getNumUnit();
        UnitQuantityChecker unitQuantityChecker = new UnitQuantityChecker(source, units);
        if (instruction instanceof Move) {
            unitQuantityChecker.setNext(new AccessibleChecker(board, source, dest));
        } else if (instruction instanceof Attack) {
            if (source.getOwner().equals(dest.getOwner())) {
                System.out.println("Attack failed because of having same owner. Source: " + source.getName() + ", Destination: " + dest.getName());
                return false;
            }
            unitQuantityChecker.setNext(new AdjacentChecker(board, source, dest));
        }
        return unitQuantityChecker.isValid();
    }
}
