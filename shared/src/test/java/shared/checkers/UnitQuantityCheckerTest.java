package shared.checkers;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import shared.Region;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitQuantityCheckerTest {

    @Test
    public void isValid() {
        Region r1 = mock(Region.class);
        when(r1.getNumBaseUnit()).thenReturn(4);
        UnitQuantityChecker unitQuantityChecker = new UnitQuantityChecker(r1, 3);
        Assertions.assertTrue(unitQuantityChecker.isValid());

        UnitQuantityChecker unitQuantityChecker1 = new UnitQuantityChecker(r1, 4);
        Assertions.assertTrue(unitQuantityChecker1.isValid());

        UnitQuantityChecker unitQuantityChecker2 = new UnitQuantityChecker(r1, 5);
        Assertions.assertFalse(unitQuantityChecker2.isValid());
    }
}