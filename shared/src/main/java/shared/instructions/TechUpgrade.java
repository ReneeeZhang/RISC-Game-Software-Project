package shared.instructions;

import java.io.Serializable;
import shared.*;
import shared.checkers.*;

public class TechUpgrade extends UpgradeInstruction implements Serializable {
  
  private static final long serialVersionUID = 923749347;

  public TechUpgrade(String pname, int oldL, int newL) {
    super(pname, oldL, newL);
  }

  @Override
  public void execute(Board b) {
    Player p = b.getPlayer(playerName);
    p.upgradeTech();
    p.decreaseTech(getCost(new UpgradeLookup()));
 } 

  @Override
  public boolean isValid(Board b) {
    // TODO: use checker!!!
    return true;
  }

  public int getCost(UpgradeLookup table) {
    return table.getCostTech(newLevel);
  }
}
