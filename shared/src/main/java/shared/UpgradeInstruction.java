package shared;

import java.io.Serializable;
import shared.checkers.*;

abstract public class UpgradeInstruction implements Instruction, Serializable{
  protected String src;
  protected int level;
  private static final long serialVersionUID = 435352124;

  // Default constructor
  public UpgradeInstruction() {
  }

  // Constructor
  public UpgradeInstruction(String s, int l) {
    this.src = s;
    this.level = l;
  }

  // Getters
  public String getSrc() {
    return src;
  }

  public int getLevel() {
    return level;
  }
}
