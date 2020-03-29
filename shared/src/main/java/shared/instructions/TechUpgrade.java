package shared.instructions;

import java.io.Serializable;
import shared.*;
import shared.checkers.*;

public class TechUpgrade extends UpgradeInstruction implements Serializable {
  
  private static final long serialVersionUID = 923749347;

  public TechUpgrade(int oldL, int newL) {
    super(oldL, newL);
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


  public void execute(Player p) {
    p.upgrade();
    p.decreaseTech(getCost(new UpgradeLookup()));
  }

  public boolean isValid(Player p) {
    return true;
  }

  public int getCost(UpgradeLookup table) {
    return table.getCostTech(newLevel);
  }
}
