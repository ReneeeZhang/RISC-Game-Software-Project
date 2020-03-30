package shared;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BaseRegionTest {
  private BaseRegion br1;
  private BaseRegion br2;

  public BaseRegionTest() {
    List<BaseUnit> units1 = new ArrayList<>();
    Map<String, List<BaseUnit>> bc1 = new HashMap<>();
    bc1.put("Teer", new ArrayList<>());
    units1.add(new BaseUnit());
    br1 = new BaseRegion("Hudson", "Player1", "", 11, units1, bc1);

    br2 = new BaseRegion("Teer", "Player2", 12);
    br1.initOneBorderCamp("Teer");
  }
  @Test
  public void test_getters() {
    String name1 = br1.getName();
    assertTrue(name1.equals("Hudson"));
    String owner1 = br1.getOwner();
    assertTrue(owner1.equals("Player1"));
    int num1 = br1.getNumBaseUnit();
    assertTrue(num1 == 1);
  }

  @Test
  public void test_sendUnit() {
    List<BaseUnit> s = br1.sendUnit(1);
    br1.receiveUnit(s);
    br1.setOwner("aa");
    br1.autoIncrement();
    br1.autoIncrement();
    br1.removeUnit();
    br1.autoIncrement();
    br1.removeUnit(1);
    br2.initOneBorderCamp("sdf");
    br2.getBorderCamp("sdf");
    br1.getColor();
    br1.dispatch("Teer", 1);
    assertTrue(br1.getBorderCamp("Teer").size() == 1);
    assertTrue(br1.numUnitWithLevel(0) != 0);
    assertTrue(br1.numUnitWithLevel(1) == 0);
  }
}
