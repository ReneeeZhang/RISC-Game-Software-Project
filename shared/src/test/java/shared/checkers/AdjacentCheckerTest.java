package shared.checkers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.Board;
import shared.Region;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdjacentCheckerTest {

    @Test
    public void isValid() {
        Region r1 = mock(Region.class);
        Region r2 = mock(Region.class);
        Region r3 = mock(Region.class);
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
        AdjacentChecker adjacentChecker = new AdjacentChecker(boardMock, r1, r3);
        Assertions.assertTrue(adjacentChecker.isValid());
        //add next
        when(r1.getNumBaseUnit()).thenReturn(4);
        UnitQuantityChecker unitQuantityChecker = new UnitQuantityChecker(r1, 0, 3);
        adjacentChecker = new AdjacentChecker(boardMock, r1, r3, unitQuantityChecker);
        Assertions.assertTrue(adjacentChecker.isValid());
        //invalid
        List<Region> regions2 = Arrays.asList(r2);
        when(boardMock.getNeighbor("r1")).thenReturn(regions2);
        adjacentChecker = new AdjacentChecker(boardMock, r1, r3);
        Assertions.assertFalse(adjacentChecker.isValid());


    }
}
