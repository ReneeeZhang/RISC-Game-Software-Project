package shared.checkers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.Board;
import shared.Player;
import shared.UpgradeLookup;
import shared.instructions.TechUpgrade;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TechUpgradeCheckerTest {
    @Test
    public void isValid() {
        TechUpgrade instruction = mock(TechUpgrade.class);
        when(instruction.getPlayerName()).thenReturn("A");
        //player
        Player player = mock(Player.class);
        when(player.getCurrLevel()).thenReturn(4);
        when(player.getTechAmount()).thenReturn(200);
        //board
        Board board = mock(Board.class);
        when(board.getPlayer("A")).thenReturn(player);
        //valid
        TechUpgradeChecker techUpgradeChecker = new TechUpgradeChecker(board, instruction);
        Assertions.assertTrue(techUpgradeChecker.isValid());
        //top level
        when(player.getCurrLevel()).thenReturn(6);
        Assertions.assertFalse(techUpgradeChecker.isValid());
        //not enough resource
        when(player.getCurrLevel()).thenReturn(4);
        when(player.getTechAmount()).thenReturn(100);
        Assertions.assertFalse(techUpgradeChecker.isValid());
    }

}