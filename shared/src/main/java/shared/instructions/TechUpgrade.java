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
  public void execute(Board b){ 
    Player p = b.getPlayer(playerName);
    p.upgrade();
    p.decreaseTech(getCost(new UpgradeLookup()));
 } 

  @Override
  public boolean isValid(Board b) {
    TechUpgradeChecker checker= new TechUpgradeChecker(b, this);
    return checker.isValid();
  }

  public int getCost(UpgradeLookup table) {
    return table.getCostTech(newLevel);
  }
}
