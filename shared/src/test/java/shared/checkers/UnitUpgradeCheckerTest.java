package shared.checkers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.BaseRegion;
import shared.Board;
import shared.Player;
import shared.Region;
import shared.instructions.UnitUpgrade;

import static org.mockito.Mockito.*;

public class UnitUpgradeCheckerTest {

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
        //valid
        UnitUpgradeChecker unitUpgradeChecker = new UnitUpgradeChecker(board, instruction);
        Assertions.assertTrue(unitUpgradeChecker.isValid());
        //wrong owner
        when(instruction.getPlayerName()).thenReturn("B");
        unitUpgradeChecker = new UnitUpgradeChecker(board, instruction);
        Assertions.assertFalse(unitUpgradeChecker.isValid());
        //wrong lower level
        when(instruction.getPlayerName()).thenReturn("A");
        when(r1.numUnitWithLevel(4)).thenReturn(0);
        unitUpgradeChecker = new UnitUpgradeChecker(board, instruction);
        Assertions.assertFalse(unitUpgradeChecker.isValid());
        //wrong upper level
        when(r1.numUnitWithLevel(4)).thenReturn(3);
        when(instruction.getNewLevel()).thenReturn(7);
        unitUpgradeChecker = new UnitUpgradeChecker(board, instruction);
        Assertions.assertFalse(unitUpgradeChecker.isValid());
        //old >= new
        when(instruction.getNewLevel()).thenReturn(5);
        when(instruction.getOldLevel()).thenReturn(5);
        unitUpgradeChecker = new UnitUpgradeChecker(board, instruction);
        Assertions.assertFalse(unitUpgradeChecker.isValid());
        //cost
        when(instruction.getOldLevel()).thenReturn(4);
        when(player.getTechAmount()).thenReturn(30);
        unitUpgradeChecker = new UnitUpgradeChecker(board, instruction);
        Assertions.assertFalse(unitUpgradeChecker.isValid());
    }
}