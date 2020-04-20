package shared.checkers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import shared.BaseRegion;
import shared.Board;
import shared.Player;
import shared.Region;

public class AccessibleCheckerTest {

    @Test
    public void isValid() {
      Region r1 = mock(Region.class);
      Region r2 = mock(Region.class);
      Region r3 = mock(Region.class);
      //owner
      when(r1.getOwner()).thenReturn(new Player("A"));
      when(r2.getOwner()).thenReturn(new Player("A"));
      when(r3.getOwner()).thenReturn(new Player("A"));
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
      //add next
      when(r1.numUnitWithLevel(0)).thenReturn(4);
      UnitQuantityChecker unitQuantityChecker = new UnitQuantityChecker(r1, 0, 3);
      accessibleChecker2 = new AccessibleChecker(boardMock2, r1, r3, unitQuantityChecker);
      Assertions.assertTrue(accessibleChecker2.isValid());
      /*
          two regions belonging to different owners
       */
      //r4 belongs to "B"
      Region r4 = mock(Region.class);
      when(r4.getName()).thenReturn("r4");
      when(r4.getOwner()).thenReturn(new Player("B"));
      //r1 -> r2, r1 -> r3, r1 -> r4
      List<Region> regions4 = Arrays.asList(r2, r3, r4);
      Board boardMock3 = mock(Board.class);
      when(boardMock3.getNeighbor("r1")).thenReturn(regions4);
      //source: r1, dest: r4
      AccessibleChecker accessibleChecker3 = new AccessibleChecker(boardMock3, r1, r4);
      Assertions.assertFalse(accessibleChecker3.isValid());

      //invalid
      List<Region> regions5 = Arrays.asList(r3);
      when(boardMock.getNeighbor("r2")).thenReturn(regions5);
      accessibleChecker3 = new AccessibleChecker(boardMock3, r1, r4);
      Assertions.assertFalse(accessibleChecker3.isValid());
    }

  @Test
    public void test_isValid() {
      Player p1 = new Player("p1");
      Player p2 = new Player("p2");
      Player p3 = new Player("p3");
      p1.allyWith(p3);
      p3.allyWith(p1);
      
      Region r1 = new BaseRegion("r1", p1, 10);
      Region r2 = new BaseRegion("r2", p2, 10);
      Region r3 = new BaseRegion("r3", p3, 10);

      List<Region> regions1 = Arrays.asList(r2);
      List<Region> regions2 = Arrays.asList(r1, r3);
      List<Region> regions3 = Arrays.asList(r2);
      Board boardMock2 = mock(Board.class);
      when(boardMock2.getNeighbor("r1")).thenReturn(regions1);
      when(boardMock2.getNeighbor("r2")).thenReturn(regions2);
      when(boardMock2.getNeighbor("r3")).thenReturn(regions3);

      AccessibleChecker ac = new AccessibleChecker(boardMock2, r1, r3);
      assertFalse(ac.isValid());
    }
}
