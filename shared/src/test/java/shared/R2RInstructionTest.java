package shared;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

public class R2RInstructionTest {
  @Test
  public void test_() {
    R2RInstruction ins = new R2RInstruction("r1", "r2", 1);
    //assertEquals(true, ins.isValid());

    Region r1 = mock(Region.class);
    Region r2 = mock(Region.class);
   
    //owner
    when(r1.getOwner()).thenReturn("A");
    when(r2.getOwner()).thenReturn("A");
  
    //name
    when(r1.getName()).thenReturn("r1");
    when(r2.getName()).thenReturn("r2");

    Board boardMock = mock(Board.class);
    when(boardMock.getRegion("r1")).thenReturn(r1);
    when(boardMock.getRegion("r2")).thenReturn(r2);

    ins.execute(boardMock);
    assertEquals("r1", ins.getSrc().getName());
    assertEquals("r2", ins.getDest().getName());
  }

}
