package shared.checkers;

import shared.*;
import shared.instructions.UnitUpgrade;

public class UnitUpgradeChecker implements Checker {

    Board board;
    UnitUpgrade instruction;
    Checker next;

    public UnitUpgradeChecker(Board board, UnitUpgrade instruction, Checker next) {
        this.board = board;
        this.instruction = instruction;
        this.next = next;
    }

    public UnitUpgradeChecker(Board board, UnitUpgrade instruction) {
        this(board, instruction, null);
    }


    @Override
    public boolean isValid() {
        BaseRegion region = (BaseRegion)board.getRegion(instruction.getSrc());
        String playerName = instruction.getPlayerName();
        if (!playerName.equals(region.getOwner())) {
            System.out.println(String.format("Unit Upgrade failed because of inconsistent owner. Caller: %s, Region owner: %s", playerName, region.getOwner()));
            return false;
        }
        int newLevel = instruction.getNewLevel();
        int oldLevel = instruction.getOldLevel();

        //TODO: check lower level
        int num = instruction.getNumUnit();
        for (Unit unit : region.)
        //TODO:check upper bound
        if (!new UpgradeLookup().validUnitLevel(newLevel)) {
            System.out.println("Unit Upgrade failed because level is out of bound");
            return false;
        }
        //check cost
        int cost = instruction.getCost(new UpgradeLookup());
        Player player = board.getPlayer(playerName);
        if (player.getTechAmount() < cost) {
            System.out.println(String.format("Unit Upgrade failed because of lacing technology resource. Expected: %d, Having: %d", cost, player.getTechAmount()));
            return false;
        }
        return next == null || next.isValid();
    }
}
