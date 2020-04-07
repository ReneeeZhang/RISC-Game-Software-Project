package shared.checkers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.Board;
import shared.Region;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameOverCheckerTest {

    @Test
    public void isValid() {
        Region r1 = mock(Region.class);
        Region r2 = mock(Region.class);
        Region r3 = mock(Region.class);
        //owner
        when(r1.getOwner()).thenReturn("A");
        when(r2.getOwner()).thenReturn("A");
        when(r3.getOwner()).thenReturn("A");

        //over
        List<Region> regions = Arrays.asList(r1, r2, r3);
        Board boardMock = mock(Board.class);
        when(boardMock.getAllRegions()).thenReturn(regions);
        GameOverChecker gameOverChecker = new GameOverChecker(boardMock);
        Assertions.assertTrue(gameOverChecker.isValid());

        WinnerChecker winnerChecker = new WinnerChecker(boardMock, "A");
        gameOverChecker = new GameOverChecker(boardMock, winnerChecker);
        Assertions.assertTrue(gameOverChecker.isValid());

        winnerChecker = new WinnerChecker(boardMock, "B");
        gameOverChecker = new GameOverChecker(boardMock, winnerChecker);
        Assertions.assertFalse(gameOverChecker.isValid());

        //not
        when(r3.getOwner()).thenReturn("B");
        gameOverChecker = new GameOverChecker(boardMock);
        Assertions.assertFalse(gameOverChecker.isValid());


    }
}