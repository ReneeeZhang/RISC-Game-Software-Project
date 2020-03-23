package shared.checkers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.Board;
import shared.Region;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WinnerCheckerTest {

    @Test
    public void isValid() {
        Region r1 = mock(Region.class);
        Region r2 = mock(Region.class);
        Region r3 = mock(Region.class);
        //owner
        when(r1.getOwner()).thenReturn("A");
        when(r2.getOwner()).thenReturn("A");
        when(r3.getOwner()).thenReturn("A");
        //name
        when(r1.getName()).thenReturn("r1");
        when(r2.getName()).thenReturn("r2");
        when(r3.getName()).thenReturn("r3");

        List<Region> regions = Arrays.asList(r1, r2, r3);
        Board boardMock = mock(Board.class);
        when(boardMock.getAllRegions()).thenReturn(regions);

        WinnerChecker winnerChecker = new WinnerChecker(boardMock, "A");
        Assertions.assertTrue(winnerChecker.isValid());

        when(r3.getOwner()).thenReturn("B");
        Assertions.assertFalse(winnerChecker.isValid());
    }
}