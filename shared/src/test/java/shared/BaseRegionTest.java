package shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class BaseRegionTest {
  private BaseRegion br1;
  private BaseRegion br2;

  public BaseRegionTest() {
    List<Unit> units1 = new ArrayList<>();
    Map<String, List<Unit>> bc1 = new HashMap<>();
    bc1.put("Teer", new ArrayList<>());
    units1.add(new BaseUnit());
    br1 = new BaseRegion("Hudson", "Player1", "", units1, bc1);

    br2 = new BaseRegion("Teer", "Player2");
  }
  @Test
  public void test_getters() {
    String name1 = br1.getName();
    String owner1 = br1.getOwner();
    int num1 = br1.getNumBaseUnit();
  }

  @Test
  public void test_sendUnit() {
    List<Unit> s = br1.sendUnit(1);
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
  }
  
  

}
