package shared.instructions;

import java.io.Serializable;
import shared.*;
import shared.checkers.*;
import javafx.util.Pair;
import java.util.HashMap;

public class UnitUpgrade extends UpgradeInstruction implements Serializable {
  private String src;
  private HashMap<Integer, Pair<Integer, Integer>> upMap;
  private static final long serialVersionUID = 923749348;

  public UnitUpgrade(String s, int oldL, int newL) {
    super(oldL, newL);
    this.src = s;
    // level:(bonus, cost) in a map
    this.upMap = new HashMap<Integer, Pair<Integer, Integer>>();
    this.upMap.put(0, new Pair<Integer, Integer>(0, 0));
    this.upMap.put(1, new Pair<Integer, Integer>(1, 3));
    this.upMap.put(2, new Pair<Integer, Integer>(3, 11));
    this.upMap.put(3, new Pair<Integer, Integer>(5, 30));
    this.upMap.put(4, new Pair<Integer, Integer>(8, 55));
    this.upMap.put(5, new Pair<Integer, Integer>(11, 90));
    this.upMap.put(6, new Pair<Integer, Integer>(15, 140));
    this.cost = upMap.get(newL).getValue() - upMap.get(oldL).getValue();
  }

  @Override
  public void execute(Board b) {
    //b.upgrade(src, oldLevel, newLevel);
  }

  @Override
  public boolean isValid(Board b) {
    return true;
  }

  // public void execute(Player p) {
  //   p.upgrade();
  // }

  // public boolean isValid(Player p) {
  //   return true;
  // }
  
  // Getters
  public String getSrc() {
    return src;
  }
}
