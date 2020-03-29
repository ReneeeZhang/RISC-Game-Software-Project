package shared.instructions;

import java.io.Serializable;
import shared.*;
import shared.checkers.*;

abstract public class UpgradeInstruction implements Instruction, Serializable{
  protected int oldLevel;
  protected int newLevel;
  private static final long serialVersionUID = 435352124;

  // Default constructor
  public UpgradeInstruction() {
  }
  
  // Constructor
  public UpgradeInstruction(int oldL, int newL) {
    this.oldLevel = oldL;
    this.newLevel = newL;
  }

  public int getOldLevel() {
    return oldLevel;
  }
  
  public int getNewLevel() {
    return newLevel;
  }
}
