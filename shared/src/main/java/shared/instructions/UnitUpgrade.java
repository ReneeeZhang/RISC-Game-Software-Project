package shared.instructions;

import java.io.Serializable;
import shared.*;
import shared.checkers.*;


public class UnitUpgrade extends UpgradeInstruction implements Serializable {
  private String src;
  private int numUnit;
  
  private static final long serialVersionUID = 923749348;

  public UnitUpgrade(String s, int oldL, int newL, int num) {
    super(oldL, newL);
    this.src = s;
    this.numUnit = num;
  }

  /* NOT USING */
  @Override
  public void execute(Board b) {
  }

  @Override
  public boolean isValid(Board b) {
    return true;
  }
  /* NOT USING */

  public void execute(Board b, Player p) {
    //Region source = b.getRegion(src);
    //source.upgradeUnit(oldLevel, newLevel, numUnit);
    p.decreaseTech(getCost(new UpgradeLookup()));
  }

  public boolean isValid(Board b, Player p) {
    return true;
  }
  
  // Getters
  public String getSrc() {
    return src;
  }

  public int getCost(UpgradeLookup table) {
    return table.getCostUnit(oldLevel, newLevel);
  }
}
