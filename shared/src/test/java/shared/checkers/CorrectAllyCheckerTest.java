package shared.checkers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import shared.Player;

public class CorrectAllyCheckerTest {
  @Test
  public void test_isValid() {
    Player p1 = new Player("p1");
    Player p2 = new Player("p2");
    p1.allyWith(p2);
    CorrectAllyChecker caChecker = new CorrectAllyChecker(p1, p2);
    assertTrue(caChecker.isValid());

    CorrectAllyChecker caChecker2 = new CorrectAllyChecker(p1, new Player("p3"), caChecker);
    assertFalse(caChecker2.isValid());
  }

}
