package shared.checkers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.Board;
import shared.Player;
import shared.Region;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoserCheckerTest {
    @Test
    public void isValid() {
        Region r1 = mock(Region.class);
        Region r2 = mock(Region.class);
        Region r3 = mock(Region.class);
        //owner
        Player A = new Player("A");
        when(r1.getOwner()).thenReturn(A);
        when(r2.getOwner()).thenReturn(A);
        when(r3.getOwner()).thenReturn(A);

        List<Region> regions = Arrays.asList(r1, r2, r3);
        Board boardMock = mock(Board.class);
        when(boardMock.getAllRegions()).thenReturn(regions);

        LoserChecker loserChecker = new LoserChecker(boardMock, "B");
        Assertions.assertTrue(loserChecker.isValid());

        when(r3.getOwner()).thenReturn(new Player("B"));
        Assertions.assertFalse(loserChecker.isValid());
    }
}
