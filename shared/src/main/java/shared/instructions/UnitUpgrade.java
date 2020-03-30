package shared.instructions;

import java.io.Serializable;
import shared.*;
import shared.checkers.*;


public class UnitUpgrade extends UpgradeInstruction implements Serializable {
  private String src;
  private int numUnit;
  
  private static final long serialVersionUID = 923749348;

  public UnitUpgrade(String pname, String s, int oldL, int newL, int num) {
    super(pname, oldL, newL);
    this.src = s;
    this.numUnit = num;
  }

  @Override
  public void execute(Board b) {
    Player p = b.getPlayer(playerName);
    //Region source = b.getRegion(src);
    //source.upgradeUnit(oldLevel, newLevel, numUnit);
    p.decreaseTech(getCost(new UpgradeLookup()));
  }

  @Override
  public boolean isValid(Board b) {
    // TODO: use checker!!!
    return true;
  }
  
  // Getters
  public String getSrc() {
    return src;
  }

  public int getCost(UpgradeLookup table) {
    return table.getCostUnit(oldLevel, newLevel);
  }

    public int getNumUnit() {
        return numUnit;
    }
}
