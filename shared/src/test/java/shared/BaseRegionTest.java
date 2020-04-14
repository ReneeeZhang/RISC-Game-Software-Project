package shared;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
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
    Player player1 = new Player("player1");
    Player player2 = new Player("player2");
    br1 = new BaseRegion("Hudson", player1, 11);
    br2 = new BaseRegion("Teer", player2, 12);
    br1.initOneBorderCamp("Teer");
  }

  @Test
  public void test_getters() {
    String name1 = br1.getName();
    assertTrue(name1.equals("Hudson"));
    Player owner1 = br1.getOwner();
    assertTrue(owner1.getName().equals("player1"));
    int num1 = br1.getNumBaseUnit();
    System.out.println("num1=------" + num1);
    assertTrue(num1 == 5);
    int resource = br1.getResourceProduction();
  }

  @Test
  public void test_sendUnit() {
    List<BaseUnit> s = br1.sendUnit(1);
    br1.receiveUnit(s);
    Player aa = new Player("aa");
    br1.setOwner(aa);
    br1.autoIncrement();
    br1.autoIncrement();
    br1.removeUnit();
    br1.autoIncrement();
    br1.removeUnit(1);
    br2.initOneBorderCamp("sdf");
    br2.getBorderCamp("sdf");
    br1.dispatch("Teer", 1);
    assertTrue(br1.getBorderCamp("Teer").size() == 1);
    assertTrue(br1.numUnitWithLevel(0) != 0);
    assertTrue(br1.numUnitWithLevel(1) == 0);
  }

  @Test
  public void test_getInfo() {
    System.out.println(br1.getInfo());
  }

  @Test
  public void test_getMajorCamp() {
    Collections.sort(br1.getMajorCamp());
  }

  @Test
  public void test_upgrade() {
    Player rowner = new Player("rowner");
    BaseRegion reg = new BaseRegion("rname", rowner, 10);
    assertTrue(reg.getSize()==10);
    reg.upgradeUnit(0, 1, 2);
    assertTrue(reg.numUnitWithLevel(1)==2);
    assertTrue(reg.numUnitWithLevel(0)==3);
  }
}
