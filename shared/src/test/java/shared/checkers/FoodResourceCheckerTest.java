package shared.checkers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import shared.Board;
import shared.Player;
import shared.Region;

public class FoodResourceCheckerTest {
  @Test
  public void test_FoodResourceChecker() {
    Region r1 = mock(Region.class);
    Region r2 = mock(Region.class);
    //owner
    when(r1.getOwner()).thenReturn(new Player("A"));
    when(r2.getOwner()).thenReturn(new Player("A"));
    //name
    when(r1.getName()).thenReturn("r1");
    when(r2.getName()).thenReturn("r2");

    //player
    Player player = mock(Player.class);
    when(player.getFoodAmount()).thenReturn(20);
    //board
    Board boardMock = mock(Board.class);
    when(boardMock.getRegion("r1")).thenReturn(r1);
    when(boardMock.getRegion("r2")).thenReturn(r2);
    when(boardMock.getPlayer("A")).thenReturn(player);
    when(boardMock.getDistance("A", "r1", "r2")).thenReturn(9);

    FoodResourceChecker foodResourceChecker = new FoodResourceChecker(boardMock, "A", "r1", "r2", 1);
    Assertions.assertTrue(foodResourceChecker.isValid());

    when(boardMock.getDistance("A", "r1", "r2")).thenReturn(11);
    when(boardMock.getPlayer("A")).thenReturn(player);
    when(player.getFoodAmount()).thenReturn(3);
    foodResourceChecker = new FoodResourceChecker(boardMock, "A", "r1", "r2", 1);
    Assertions.assertFalse(foodResourceChecker.isValid());
  }
}
