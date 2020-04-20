package shared.checkers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import shared.Player;

public class FoodAmountCheckerTest {
  @Test
  public void test_isValid() {
    Player p = mock(Player.class);
    when(p.getFoodAmount()).thenReturn(50);
    FoodAmountChecker faChecker = new FoodAmountChecker(p, 40);
    assertTrue(faChecker.isValid());

    FoodAmountChecker faChecker2 = new FoodAmountChecker(p, 60, faChecker);
    assertFalse(faChecker2.isValid());
  }
}
