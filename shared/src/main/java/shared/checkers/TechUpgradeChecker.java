package shared.checkers;

import shared.Board;
import shared.Player;
import shared.UpgradeLookup;
import shared.instructions.TechUpgrade;

public class TechUpgradeChecker implements Checker {

    Board board;
    TechUpgrade instruction;
    Checker next;

    public TechUpgradeChecker(Board board, TechUpgrade instruction) {
        this(board, instruction, null);
    }

    public TechUpgradeChecker(Board board, TechUpgrade instruction, Checker next) {
        this.board = board;
        this.instruction = instruction;
        this.next = next;
    }

    @Override
    public boolean isValid() {
        String playerName = instruction.getPlayerName();
        Player player = board.getPlayer(playerName);
        int oldLevel = player.getCurrLevel();
        int newLevel = oldLevel + 1;

        if (!new UpgradeLookup().validTechLevel(newLevel)) {
            System.out.println(String.format("Tech Upgrade failed because it's the most advanced.Upper Current level: %d", oldLevel));
            return false;
        }
        int cost = new UpgradeLookup().getCostTech(newLevel);
        if (player.getTechAmount() < cost) {
            System.out.println(String.format("Unit Upgrade failed because of lacing technology resource. Expected: %d, Having: %d", cost, player.getTechAmount()));
            return false;
        }
        return next == null || next.isValid();
    }
}
