package shared.checkers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import shared.Board;
import shared.Region;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class AccessibleCheckerTest {

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
        /*
            two regions directly connected
         */
        //r1 -> r2, r1 -> r3
        List<Region> regions = Arrays.asList(r2, r3);
        Board boardMock = mock(Board.class);
        when(boardMock.getNeighbor("r1")).thenReturn(regions);
        //source: r1, dest: r3
        AccessibleChecker accessibleChecker = new AccessibleChecker(boardMock, r1, r3);
        Assertions.assertTrue(accessibleChecker.isValid());
        /*
            regions not directly connected
         */
        //r1 -> r2, r2 -> r3
        List<Region> regions2 = Arrays.asList(r2);
        List<Region> regions3 = Arrays.asList(r1, r3);
        Board boardMock2 = mock(Board.class);
        when(boardMock2.getNeighbor("r1")).thenReturn(regions2);
        when(boardMock2.getNeighbor("r2")).thenReturn(regions3);
        //source: r1, dest: r3
        AccessibleChecker accessibleChecker2 = new AccessibleChecker(boardMock2, r1, r3);
        Assertions.assertTrue(accessibleChecker2.isValid());
        /*
            two regions belonging to different owners
         */
        //r4 belongs to "B"
        Region r4 = mock(Region.class);
        when(r1.getOwner()).thenReturn("B");
        //r1 -> r2, r1 -> r3, r1 -> r4
        List<Region> regions4 = Arrays.asList(r2, r3, r4);
        Board boardMock3 = mock(Board.class);
        when(boardMock.getNeighbor("r1")).thenReturn(regions4);
        //source: r1, dest: r3
        AccessibleChecker accessibleChecker3 = new AccessibleChecker(boardMock3, r1, r4);
        Assertions.assertFalse(accessibleChecker3.isValid());
    }
}