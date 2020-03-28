package shared.checkers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.*;
import shared.instructions.*;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ClientInstructionCheckerTest {

    @Test
    public void isValid() {
        Region r1 = mock(Region.class);
        Region r2 = mock(Region.class);
        Region r3 = mock(Region.class);
        //name
        when(r1.getName()).thenReturn("r1");
        when(r2.getName()).thenReturn("r2");
        when(r3.getName()).thenReturn("r3");
        //units
        when(r1.getNumBaseUnit()).thenReturn(6);
        when(r2.getNumBaseUnit()).thenReturn(6);
        when((r3.getNumBaseUnit())).thenReturn(6);
        //instructions
        Move m1 = mock(Move.class);
        Move m2 = mock(Move.class);
        Move m3 = mock(Move.class);
        Attack a1 = mock(Attack.class);
        //sources
        when(m1.getSrc()).thenReturn("r1");
        when(m1.getDest()).thenReturn("r2");
        when(m2.getSrc()).thenReturn("r1");
        when(m2.getDest()).thenReturn("r3");
        when(m3.getSrc()).thenReturn("r2");
        when(m3.getDest()).thenReturn("r3");
        when(a1.getSrc()).thenReturn("r1");
        when(a1.getDest()).thenReturn("r2");
        //units
        when(m1.getNumUnit()).thenReturn(3);
        when(m2.getNumUnit()).thenReturn(3);
        when(m3.getNumUnit()).thenReturn(4);
        when(a1.getNumUnit()).thenReturn(3);
        //board
        Board boardMock = mock(Board.class);
        when(boardMock.getRegion("r1")).thenReturn(r1);
        when(boardMock.getRegion("r2")).thenReturn(r2);
        when(boardMock.getRegion("r3")).thenReturn(r3);
        //abundant units test
        List<Instruction> instructions = Arrays.asList(m1, m2, m3);
        ClientInstructionChecker clientInstructionChecker = new ClientInstructionChecker(boardMock, instructions);
        Assertions.assertTrue(clientInstructionChecker.isValid());
        //lacking units test
        when(m2.getNumUnit()).thenReturn(4);
        ClientInstructionChecker clientInstructionChecker2 = new ClientInstructionChecker(boardMock, instructions);
        Assertions.assertFalse(clientInstructionChecker2.isValid());
        //lacking units for attack
        when(m2.getNumUnit()).thenReturn(3);
        List<Instruction> instructions2 = Arrays.asList(a1, m1, m2, m3);
        ClientInstructionChecker clientInstructionChecker3 = new ClientInstructionChecker(boardMock, instructions2);
        Assertions.assertFalse(clientInstructionChecker3.isValid());
    }
}
