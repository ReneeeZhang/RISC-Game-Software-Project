package shared.checkers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.Board;
import shared.Player;
import shared.Region;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FoodResourceCheckerTest {
    @Test
    public void isValid() {
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
        when(player.getFoodAmount()).thenReturn(10);
        //board
        Board boardMock = mock(Board.class);
        when(boardMock.getPlayer("A")).thenReturn(player);
        when(boardMock.getDistance("r1", "r2")).thenReturn(9);

        FoodResourceChecker foodResourceChecker = new FoodResourceChecker(boardMock, r1, r2, 1);
        Assertions.assertTrue(foodResourceChecker.isValid());

        when(boardMock.getDistance("r1", "r2")).thenReturn(11);
        foodResourceChecker = new FoodResourceChecker(boardMock, r1, r2, 1);
        Assertions.assertFalse(foodResourceChecker.isValid());
    }
}
