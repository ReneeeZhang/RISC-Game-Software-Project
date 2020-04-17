package shared.instructions;

import java.io.Serializable;

abstract public class UpgradeInstruction implements Instruction, Serializable {
  private static final long serialVersionUID = 435352124;
  protected String playerName;
  protected int oldLevel;
  protected int newLevel;

  // Default constructor
  public UpgradeInstruction() {
  }
  
  // Constructor
  public UpgradeInstruction(String name, int oldL, int newL) {
    this.playerName = name;
    this.oldLevel = oldL;
    this.newLevel = newL;
  }

  // Getters
  public String getPlayerName() {
    return playerName;
  }

  public int getOldLevel() {
    return oldLevel;
  }
  
  public int getNewLevel() {
    return newLevel;
  }
}
