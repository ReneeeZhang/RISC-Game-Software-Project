package shared.instructions;

import shared.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;

public class MoveTest {
  @Test
  public void test_Move() {
    Region r1 = mock(Region.class);
    Region r2 = mock(Region.class);
    Region r3 = mock(Region.class);
    //owner
    when(r1.getOwner()).thenReturn(new Player("A"));
    when(r2.getOwner()).thenReturn(new Player("A"));
    when(r3.getOwner()).thenReturn(new Player("B"));
    //name
    when(r1.getName()).thenReturn("r1");
    when(r2.getName()).thenReturn("r2");
    when(r3.getName()).thenReturn("r3");
    //unit
    when(r1.getAllUnitsAmount()).thenReturn(1);
    when(r2.getAllUnitsAmount()).thenReturn(1);
    when(r3.getAllUnitsAmount()).thenReturn(1);

    when(r1.numUnitWithLevel(0)).thenReturn(1);

    List<Region> regions = Arrays.asList(r2, r3);
    Board boardMock = mock(Board.class);
    when(boardMock.getNeighbor("r1")).thenReturn(regions);
    when(boardMock.getRegion("r1")).thenReturn(r1);
    when(boardMock.getRegion("r2")).thenReturn(r2);
    when(boardMock.getRegion("r3")).thenReturn(r3);

    Player playerMock = mock(Player.class);
    when(playerMock.getFoodAmount()).thenReturn(50);

    when(boardMock.getPlayer("A")).thenReturn(playerMock);
    when(r1.numUnitWithLevel(playerMock, 0)).thenReturn(5);
    Move m1 = new Move("A", "r1", "r2", 0, 1);
    assertTrue(m1.isValid(boardMock));
    m1.execute(boardMock);

    m1.toString();

    Move m2 = new Move("A", "r1", "r3", 0, 1);
    assertFalse(m2.isValid(boardMock));
    m2.execute(boardMock);
  }
}
