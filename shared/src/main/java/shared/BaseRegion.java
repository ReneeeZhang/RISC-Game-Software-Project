package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BaseRegion implements Region, Serializable {
  private static final long serialVersionUID = 989463821;
  private String name;
  private Player owner;
  private int size;
  private int resourceProduction;
  private List<BaseUnit> majorCamp; // For moving and defensing
  private Map<String, List<BaseUnit>> borderCamps;
  private static final int minUnitLevel = 0;
  private static final int maxUnitLevel = 6;

  /*
  public BaseRegion(String name, String owner, String color, int size, List<BaseUnit> majorCamp, Map<String, List<BaseUnit>> borderCamps) {
    this.name = name;
    this.owner = owner;
    this.color = color;
    this.size = size;
    Random rand = new Random();
    this.resourceProduction = size + rand.nextInt(5);
    this.majorCamp = majorCamp;
    this.borderCamps = borderCamps;
  }
  */

  public BaseRegion(String name, Player owner, int size) {
    this.name = name;
    this.owner = owner;
    this.size = size;
    Random rand = new Random();
    this.resourceProduction = size + rand.nextInt(5);
    this.majorCamp = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      majorCamp.add(new BaseUnit());
    }
    this.borderCamps = new HashMap<>();
  }

  public String getName() {
    return this.name;
  }

  public Player getOwner() {
    return this.owner;
  }

  public int getSize() {
    return this.size;
  }

  public int getResourceProduction() {
    return this.resourceProduction;
  }
  
  public String getInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append(name).append("\nOwned by: ").append(owner).append("\nSize: ").append(size);
    sb.append("\nResource Production: ").append(resourceProduction);
    sb.append("\nUnits Info:\n");
    for (int i = minUnitLevel; i < maxUnitLevel; i++) {
      sb.append("Level ").append(i).append(": ").append(numUnitWithLevel(i)).append("\n");
    }
    return sb.toString();
  }
  
  public int getNumBaseUnit() {
    // TODO: Better way is to iterate the list of units and count all base units
    return majorCamp.size();
  }

  public List<BaseUnit> sendUnit(int num) {
    List<BaseUnit> toSend = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      toSend.add(majorCamp.remove(majorCamp.size() - 1));
    }
    return toSend;
  }

  public List<BaseUnit> sendUnit(int level, int num) {
    List<BaseUnit> toSend = new ArrayList<>();
    for (BaseUnit bu : majorCamp) {
      if (num == 0) {
        break;
      }
      if (bu.getCurrLevel() == level) {
        toSend.add(bu);
        --num;
      }
    }
    for (BaseUnit bu : toSend) {
      majorCamp.remove(bu);
    }
    return toSend;
  }

  public void receiveUnit(List<BaseUnit> toReceive) {
    for (BaseUnit unit : toReceive) {
      majorCamp.add(unit);
    }
  }

  // TODO: change method name -> loseDefenser 
  public void removeUnit(int num) {
    for (int i = 0; i < num; i++) {
      majorCamp.remove(majorCamp.size() - 1);
    }
  }

  public void removeUnit() {
    majorCamp.remove(majorCamp.size() - 1);
  }
  
  public void setOwner(Player owner) {
    this.owner = owner;
  }

  public void dispatch(String adjDest, int num) {
    List<BaseUnit> borderCamp = borderCamps.get(adjDest);
    for (int i = 0; i < num; i++) {
      borderCamp.add(majorCamp.remove(majorCamp.size() - 1));
    }
  }

  public void dispatch(String adjDest, int level, int num) {
    List<BaseUnit> borderCamp = borderCamps.get(adjDest);
    for (BaseUnit bu : majorCamp) {
      if (num == 0) {
        break;
      }
      if (bu.getCurrLevel() == level) {
        borderCamp.add(bu);
        --num;
      }
    }
    for (BaseUnit bu : borderCamp) {
       majorCamp.remove(bu);
    }
  }

  public List<BaseUnit> getMajorCamp() {
    List<BaseUnit> camp = majorCamp;
    majorCamp = new ArrayList<>();
    return camp;
  }
  
  public List<BaseUnit> getBorderCamp(String dest) {
    List<BaseUnit> troop = borderCamps.get(dest);
    borderCamps.replace(dest, new ArrayList<>());
    return troop;
  }

  public void initOneBorderCamp(String neighbor) {
    borderCamps.put(neighbor, new ArrayList<>());
  }
  
  public void autoIncrement(){
    majorCamp.add(new BaseUnit());
  }


  public void upgradeUnit(int oldLevel, int newLevel, int numUnit) {
    for (BaseUnit u: majorCamp) {
      if (u.getCurrLevel() == oldLevel && numUnit > 0) {
        u.upgradeTo(newLevel);
        numUnit--;
      }
    }
  }

  public int numUnitWithLevel(int level) {
    int num = 0;
    for (BaseUnit unit : majorCamp) {
      if (unit.getCurrLevel() == level) {
        num++;
      }
    }
    return num;
  }
}
