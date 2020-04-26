package shared.checkers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shared.Player;

public class CorrectAllyCheckerTest {
  private Player p1;
  private Player p2;

  @BeforeEach
  public void init() {
    this.p1 = new Player("p1");
    this.p2 = new Player("p2");
  }

  @Test
  public void test_isValid_noAlly() {
    CorrectAllyChecker checker = new CorrectAllyChecker(p1, p2);
    assertFalse(checker.isValid());
  }
  
  @Test
  public void test_isValid_hasCorrectAlly() {
    p1.allyWith(p2);
    p2.allyWith(p1);
    CorrectAllyChecker caChecker = new CorrectAllyChecker(p1, p2);
    assertTrue(caChecker.isValid());
  }

  @Test
  public void test_isValid_hasWrongAlly() {
    p1.allyWith(p2);
    CorrectAllyChecker caChecker = new CorrectAllyChecker(p1, new Player("p3"));
    assertFalse(caChecker.isValid());
  }

}
