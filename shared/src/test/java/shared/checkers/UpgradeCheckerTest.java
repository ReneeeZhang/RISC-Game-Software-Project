package shared.checkers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.BaseRegion;
import shared.Board;
import shared.Player;
import shared.instructions.TechUpgrade;
import shared.instructions.UnitUpgrade;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpgradeCheckerTest {

    @Test
    public void isValid() {
        UnitUpgrade instruction = mock(UnitUpgrade.class);
        when(instruction.getOldLevel()).thenReturn(4);
        when(instruction.getNewLevel()).thenReturn(5);
        when(instruction.getNumUnit()).thenReturn(3);
        when(instruction.getPlayerName()).thenReturn("A");
        when(instruction.getSrc()).thenReturn("r1");
        //player
        Player player = mock(Player.class);
        when(player.getTechAmount()).thenReturn(100);
        //region
        BaseRegion r1 = mock(BaseRegion.class);
        when(r1.getOwner()).thenReturn("A");
        when(r1.numUnitWithLevel(4)).thenReturn(3);
        //board
        Board board = mock(Board.class);
        when(board.getPlayer("A")).thenReturn(player);
        when(board.getRegion("r1")).thenReturn(r1);
        //valid unit
        UpgradeChecker upgradeChecker = new UpgradeChecker(board, instruction);
        Assertions.assertTrue(upgradeChecker.isValid());
        //valid tech
        TechUpgrade instruction1 = mock(TechUpgrade.class);
        when(instruction1.getPlayerName()).thenReturn("A");
        //player
        when(player.getCurrLevel()).thenReturn(4);
        when(player.getTechAmount()).thenReturn(200);
        upgradeChecker = new UpgradeChecker(board, instruction1);
        Assertions.assertTrue(upgradeChecker.isValid());
    }
}