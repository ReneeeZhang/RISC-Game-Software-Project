package shared.checkers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shared.Player;

public class InciteDefectionTargetCheckerTest {
  private Player p1;
  private Player p2;
  private Player p3;
  private Player sameP1;

  @BeforeEach
  public void init() {
    this.p1 = new Player("p1");
    this.p2 = new Player("p2");
    this.p3 = new Player("p3");
    this.sameP1 = new Player("p1");
  }

  @Test
  public void test_isValid_sameTargetAsInciter() {
    InciteDefectionTargetChecker checker = new InciteDefectionTargetChecker(p1, sameP1);
    assertFalse(checker.isValid());
  }

  @Test
  public void test_isValid_noAlly_valid() {
    InciteDefectionTargetChecker checker = new InciteDefectionTargetChecker(p1, p2);
    assertTrue(checker.isValid());
  }

  @Test
  public void test_isValid_hasAlly_invaild() {
    p1.allyWith(p2);
    p2.allyWith(p1);
    InciteDefectionTargetChecker chekcer = new InciteDefectionTargetChecker(p1, p2);
    assertFalse(chekcer.isValid());
  }

  @Test
  void test_isValid_hasAlly_valid() {
    p1.allyWith(p2);
    p2.allyWith(p1);
    InciteDefectionTargetChecker checker = new InciteDefectionTargetChecker(p1, p3);
    assertTrue(checker.isValid());
  }

  @Test
  void test_isValid_next() {
    p1.allyWith(p2);
    p2.allyWith(p1);
    InciteDefectionTargetChecker checker1 = new InciteDefectionTargetChecker(p1, p3);
    InciteDefectionTargetChecker checker = new InciteDefectionTargetChecker(p1, p3, checker1);
    assertTrue(checker.isValid());
  }
}
