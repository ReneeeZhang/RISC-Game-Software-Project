package shared;

import java.io.Serializable;
import shared.checkers.*;
import java.util.HashMap;

public class TechUpgrade extends UpgradeInstruction implements Serializable {
  private HashMap<Integer, Integer> upMap;
  private static final long serialVersionUID = 923749347;

  public TechUpgrade(int oldL, int newL) {
    super(oldL, newL);
    // level:cost
    this.upMap = new HashMap<Integer, Integer>();
    this.upMap.put(2, 50);
    this.upMap.put(3, 75);
    this.upMap.put(4, 125);
    this.upMap.put(5, 200);
    this.upMap.put(6, 300);
  }

  @Override
  public void execute(Board b) {
    //b.upgrade(oldLevel, newLevel);
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
}
