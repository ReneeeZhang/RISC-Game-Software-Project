package shared.checkers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class R2RInstructionCheckerTest {
    @Test
    public void isValid() {
        Region r1 = mock(Region.class);
        Region r2 = mock(Region.class);
        Region r3 = mock(Region.class);
        //name
        when(r1.getName()).thenReturn("r1");
        when(r2.getName()).thenReturn("r2");
        when(r3.getName()).thenReturn("r3");
        //owner
        when(r1.getOwner()).thenReturn("A");
        when(r2.getOwner()).thenReturn("A");
        when(r3.getOwner()).thenReturn("B");
        //units
        when(r1.getNumBaseUnit()).thenReturn(6);
        when(r2.getNumBaseUnit()).thenReturn(6);
        when((r3.getNumBaseUnit())).thenReturn(6);
        //instructions
        Move m1 = mock(Move.class);
        Attack a2 = mock(Attack.class);
        Attack a1 = mock(Attack.class);
        //sources
        when(m1.getSrc()).thenReturn(r1);
        when(m1.getDest()).thenReturn(r2);
        when(a2.getSrc()).thenReturn(r3);
        when(a2.getDest()).thenReturn(r2);
        when(a1.getSrc()).thenReturn(r1);
        when(a1.getDest()).thenReturn(r2);
        //units
        when(m1.getNumUnit()).thenReturn(3);
        when(a2.getNumUnit()).thenReturn(3);
        when(a1.getNumUnit()).thenReturn(3);
        //board
        //r1 -> r2, r1 -> r3
        List<Region> regions = Arrays.asList(r2, r3);
        Board boardMock = mock(Board.class);
        when(boardMock.getNeighbor("r1")).thenReturn(regions);

        //valid move
        R2RInstructionChecker r2RInstructionChecker = new R2RInstructionChecker(boardMock, m1);
        Assertions.assertTrue(r2RInstructionChecker.isValid());

        //invalid move
        List<Region> regions2 = Arrays.asList(r3);
        boardMock = mock(Board.class);
        when(boardMock.getNeighbor("r1")).thenReturn(regions2);
        r2RInstructionChecker = new R2RInstructionChecker(boardMock, m1);
        Assertions.assertFalse(r2RInstructionChecker.isValid());

        //invalid attack because of same owner
        List<Region> regions3 = Arrays.asList(r2, r3);
        boardMock = mock(Board.class);
        when(boardMock.getNeighbor("r1")).thenReturn(regions);
        r2RInstructionChecker = new R2RInstructionChecker(boardMock, a1);
        Assertions.assertFalse(r2RInstructionChecker.isValid());

        //valid attack
        when(boardMock.getNeighbor("r3")).thenReturn(Arrays.asList(r2));
        r2RInstructionChecker = new R2RInstructionChecker(boardMock, a2);
        Assertions.assertTrue(r2RInstructionChecker.isValid());

        //invalid attack
        when(a2.getNumUnit()).thenReturn(7);
        r2RInstructionChecker = new R2RInstructionChecker(boardMock, a2);
        Assertions.assertFalse(r2RInstructionChecker.isValid());
    }
}