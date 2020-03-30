package shared.checkers;

import shared.Board;
import shared.instructions.TechUpgrade;
import shared.instructions.UnitUpgrade;
import shared.instructions.UpgradeInstruction;

public class UpgradeChecker implements Checker{
    Board board;
    UpgradeInstruction instruction;


    public UpgradeChecker(Board board, UpgradeInstruction instruction) {
        this.board = board;
        this.instruction = instruction;
    }

    @Override
    public boolean isValid() {
        boolean valid = true;
        if (instruction instanceof TechUpgrade) {
            valid = new TechUpgradeChecker(board, (TechUpgrade)instruction).isValid();
        } else {
            valid = new UnitUpgradeChecker(board, (UnitUpgrade)instruction).isValid();
        }
        return valid;
    }
}
