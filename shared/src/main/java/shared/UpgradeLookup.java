package shared;

import java.util.HashMap;
import javafx.util.Pair;

public class UpgradeLookup {
  private HashMap<Integer, Integer> upMapTech;
  private HashMap<Integer, Pair<Integer, Integer>> upMapUnit;

  public UpgradeLookup() {
    // tech upgrade: (level-- cost)
    this.upMapTech = new HashMap<Integer, Integer>();
    this.upMapTech.put(2, 50);
    this.upMapTech.put(3, 75);
    this.upMapTech.put(4, 125);
    this.upMapTech.put(5, 200);
    this.upMapTech.put(6, 300);

    // unit upgrade: [level--(bonus--toalCost)]
    this.upMapUnit = new HashMap<Integer, Pair<Integer, Integer>>();
    this.upMapUnit.put(0, new Pair<Integer, Integer>(0, 0));
    this.upMapUnit.put(1, new Pair<Integer, Integer>(1, 3));
    this.upMapUnit.put(2, new Pair<Integer, Integer>(3, 11));
    this.upMapUnit.put(3, new Pair<Integer, Integer>(5, 30));
    this.upMapUnit.put(4, new Pair<Integer, Integer>(8, 55));
    this.upMapUnit.put(5, new Pair<Integer, Integer>(11, 90));
    this.upMapUnit.put(6, new Pair<Integer, Integer>(15, 140));
  }

  // Getter for tech
  public int getCostTech(int newLevel) {
    if (newLevel < 2) {
      return 0;
    }
    return upMapTech.get(newLevel);
  }
  
  // Getters for unit
  public int getCostUnit(int oldLevel, int newLevel) {
    return upMapUnit.get(newLevel).getValue() - upMapUnit.get(oldLevel).getValue();
  }

  public int getBonus(int level) {
    return upMapUnit.get(level).getKey();
  }

  public boolean validTechLevel(int n) {
    if (upMapTech.get(n) == null || n < 1) {
      return false;
    }
    return true;
  }

  public boolean validUnitLevel(int n) {
    if (upMapUnit.get(n) == null) {
      return false;
    }
    return true;
  }
}
