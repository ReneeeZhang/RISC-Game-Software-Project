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
        int newLevel = instruction.getNewLevel();
        int oldLevel = instruction.getOldLevel();
        if (newLevel - oldLevel > 1) {
            System.out.println("Tech Upgrade failed because you can only go up one level at a time.");
            return false;
        }
        int cost = instruction.getCost(new UpgradeLookup());
        if (player.getTechAmount() < cost) {
            System.out.println(String.format("Unit Upgrade failed because of lacing technology resource. Expected: %d, Having: %d", cost, player.getTechAmount()));
            return false;
        }
        return next == null || next.isValid();
    }
}
