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
  private Map<Integer, List<BaseUnit>> majorCamp; // For moving and defensing
  private Map<String, List<BaseUnit>> borderCamps;
  private static final int MIN_UNIT_LEVEL = 0;
  private static final int MAX_UNIT_LEVEL = 6;

  private void buildMajorCamp() {
    this.majorCamp = new HashMap<>();
    for (int i = MIN_UNIT_LEVEL; i <= MAX_UNIT_LEVEL; i++) {
      List<BaseUnit> levelCamp = new ArrayList<>();
      this.majorCamp.put(i, levelCamp);
    }
    for (int i = 0; i < 5; i++) {
      majorCamp.get(MIN_UNIT_LEVEL).add(new BaseUnit(owner));
    }
  }

  public BaseRegion(String name, Player owner, int size) {
    this.name = name;
    this.owner = owner;
    this.size = size;
    Random rand = new Random();
    this.resourceProduction = size + rand.nextInt(5);
    buildMajorCamp();
    this.borderCamps = new HashMap<>();
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Player getOwner() {
    return this.owner;
  }

  @Override
  public int getSize() {
    return this.size;
  }

  @Override
  public int getResourceProduction() {
    return this.resourceProduction;
  }

  // TODO: delete this
  @Override
  public String getInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append(name).append("\nOwned by: ").append(owner).append("\nSize: ").append(size);
    sb.append("\nResource Production: ").append(resourceProduction);
    sb.append("\nUnits Info:\n");
    // TODO: add if else
    for (int i = MIN_UNIT_LEVEL; i < MAX_UNIT_LEVEL; i++) {
      sb.append("Level ").append(i).append(": ").append(numUnitWithLevel(i)).append("\n");
    }
    return sb.toString();
  }
  
  @Override
  public String getInfo(String player) {
    StringBuilder sb = new StringBuilder();
    sb.append(name).append("\nOwned by: ").append(owner).append("\nSize: ").append(size);
    sb.append("\nResource Production: ").append(resourceProduction);
    sb.append("\nUnits Info:\n");
    // TODO: add if else
    if (player == owner.getName()) {
      for (int i = MIN_UNIT_LEVEL; i < MAX_UNIT_LEVEL; i++) {
        sb.append("Level ").append(i).append(": ").append(numUnitWithLevel(i)).append("\n");
      }
    } else if (owner.getAlly() != null && player == owner.getAlly().getName()) {
      // if has ally
      
    }
    return sb.toString();
  }

  @Override
  public int getAllUnitsAmount() {
    int amount = 0;
    for (int level : majorCamp.keySet()) {
      amount += majorCamp.get(level).size();
    }
    return amount;
  }
  
  /**
   * Send unit(s) which belong to this Region owner with level and num  
   */
  public List<BaseUnit> sendUnit(int level, int num) {
    return sendUnit(owner, level, num);
  }

  /**
   * Send unit(s) which belong to whoOwns with level and num
   */
  public List<BaseUnit> sendUnit(Player whoOwns, int level, int num) {
    List<BaseUnit> toSend = new ArrayList<>();
    List<BaseUnit> levelCamp = majorCamp.get(level);
    for (BaseUnit bu : levelCamp) {
      if (num == 0) {
        break;
      }
      if (bu.getOwner().equals(whoOwns) && bu.getCurrLevel() == level) {
        toSend.add(bu);
        --num;
      }
    }
    for (BaseUnit bu : toSend) {
      levelCamp.remove(bu);
    }
    return toSend;
  }

  public void receiveUnit(List<BaseUnit> toReceive) {
    for (BaseUnit unit : toReceive) {
      int level = unit.getCurrLevel();
      majorCamp.get(level).add(unit);
    }
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }
  
  public void dispatch(String adjDest, int level, int num) {
    dispatch(adjDest, owner, level, num);
  }

  public void dispatch(String adjDest, Player whoOwns, int level, int num) {
    List<BaseUnit> borderCamp = borderCamps.get(adjDest);
    List<BaseUnit> levelCamp = majorCamp.get(level);
    for (BaseUnit bu : levelCamp) {
      if (num == 0) {
        break;
      }
      if (bu.getOwner().equals(whoOwns) && bu.getCurrLevel() == level) {
        borderCamp.add(bu);
        --num;
      }
    }
    for (BaseUnit bu : borderCamp) {
      levelCamp.remove(bu);
    }
  }

  public List<BaseUnit> getDefenseTroop() {
    List<BaseUnit> troop = new ArrayList<>();
    for (int i = MIN_UNIT_LEVEL; i <= MAX_UNIT_LEVEL; i++) {
      troop.addAll(majorCamp.get(i));
      // Clear this level camp
      majorCamp.put(i, new ArrayList<>());
    }
    return troop;
  }

  public List<BaseUnit> getBorderCamp(String dest) {
    List<BaseUnit> troop = borderCamps.get(dest);
    borderCamps.replace(dest, new ArrayList<>());
    return troop;
  }

  public void initOneBorderCamp(String neighbor) {
    borderCamps.put(neighbor, new ArrayList<>());
  }

  public void autoIncrement() {
    majorCamp.get(MIN_UNIT_LEVEL).add(new BaseUnit(owner));
  }

  public void upgradeUnit(int oldLevel, int newLevel, int numUnit) {
    int count = numUnit;
    List<BaseUnit> oldLevelCamp = majorCamp.get(oldLevel);
    for (BaseUnit u : oldLevelCamp) {
      if (count == 0) {
        break;
      }
      u.upgradeTo(newLevel);
      majorCamp.get(newLevel).add(u);
      --count;
    }

    for (int i = 0; i < numUnit; i++) {
      oldLevelCamp.remove(0);
    }
  }

  //TODO: add Player
  public int numUnitWithLevel(int level) {
    return numUnitWithLevel(owner, level);
    
  }

  public int numUnitWithLevel(Player whoOwns, int level) {
    int num = 0;
    List<BaseUnit> levelCamp = majorCamp.get(level);
    for (BaseUnit bu : levelCamp) {
      if (bu.getOwner().equals(whoOwns)) {
        num++;
      }
    }
    return num;
  }
}
