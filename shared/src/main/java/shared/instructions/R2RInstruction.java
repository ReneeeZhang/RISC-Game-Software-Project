package shared.instructions;

import java.io.Serializable;

import shared.Board;

abstract public class R2RInstruction implements Instruction, Serializable {
  protected String src;
  protected String dest;
  protected int level;
  protected int numUnit;
  private static final long serialVersionUID = 435352123;

  // Constructor
  public R2RInstruction(String s, String d, int l, int n) {
    this.src = s;
    this.dest = d;
    this.level = l;
    this.numUnit = n;
  }

  abstract public void execute(Board b);

  abstract public boolean isValid(Board b);

  // Getters
  public String getSrc() {
    return src;
  }

  public String getDest() {
    return dest;
  }

  public int getLevel() {
    return level;
  }

  public int getNumUnit() {
    return numUnit;
  }
}
