
package shared;
import shared.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class AttackTest {
  @Test
  public void mock_() {
    Region r1 = mock(Region.class);
    Region r2 = mock(Region.class);
    Region r3 = mock(Region.class);
    //owner
    when(r1.getOwner()).thenReturn("A");
    when(r2.getOwner()).thenReturn("A");
    when(r3.getOwner()).thenReturn("B");
    //name
    when(r1.getName()).thenReturn("r1");
    when(r2.getName()).thenReturn("r2");
    when(r3.getName()).thenReturn("r3");
    //unit
    when(r1.getNumBaseUnit()).thenReturn(1);
    when(r2.getNumBaseUnit()).thenReturn(1);
    when(r3.getNumBaseUnit()).thenReturn(1);

    List<Region> regions = Arrays.asList(r2, r3);
    Board boardMock = mock(Board.class);
    when(boardMock.getNeighbor("r1")).thenReturn(regions);
    when(boardMock.getRegion("r1")).thenReturn(r1);
    when(boardMock.getRegion("r2")).thenReturn(r2);
    when(boardMock.getRegion("r3")).thenReturn(r3);
    //when(boardMock.attack("r1", "r2", 1));
    //when(boardMock.attack("r1", "r3", 1));
    

    Attack a1 = new Attack("r1", "r3", 1);
    assertEquals(true, a1.isValid(boardMock));
    a1.execute(boardMock);

     Attack a2 = new Attack("r1", "r2", 1);
    assertEquals(false, a2.isValid(boardMock));
    a2.execute(boardMock);

  }
  @Test
  public void test_() {
    // ArrayList<Unit> u1 = new ArrayList<>();
    // u1.add(new BaseUnit("r1"));
    // Region r1 = new BaseRegion("1", "1", "1", u1);

    // ArrayList<Unit> u2 = new ArrayList<>();
    // u2.add(new BaseUnit("r2"));
    // Region r2 = new BaseRegion("2", "2", "2", u2);

    // ArrayList<Unit> u3 = new ArrayList<>();
    // u3.add(new BaseUnit("r3"));
    // Region r3 = new BaseRegion("3", "3", "3", u3);

    // Map<Region, List<Region>> regionMap = new HashMap<Region, List<Region>>();
    // ArrayList<Region> l1 = new ArrayList<>();
    // l1.add(r2);
    // ArrayList<Region> l2 = new ArrayList<>();
    // l2.add(r1);
    // ArrayList<Region> l3 = new ArrayList<>();
    
    // regionMap.put(r1, l1);
    // regionMap.put(r2, l2);
    // regionMap.put(r3, l3);
    
    // Board b = new GameBoard(regionMap);
    // b.toString();

    // Move m1 = new Move("1", "2", 1);
    // m1.execute(b);
    // //assertEquals(0, r1.getNumBaseUnit());
    // //assertEquals(2, r2.getNumBaseUnit());
    
    // Move m2 = new Move("2", "3", 1);
    // m2.execute(b);
    // //assertEquals(1, r3.getNumBaseUnit());
    
  }

}
