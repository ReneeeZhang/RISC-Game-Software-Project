package shared.instructions;

import java.io.Serializable;

import shared.Board;

abstract public class R2RInstruction implements Instruction, Serializable {
  private static final long serialVersionUID = 435352123;
  protected String player;
  protected String src;
  protected String dest;
  protected int level;
  protected int numUnit;

  // Constructor
  public R2RInstruction(String player, String src, String dest, int level, int num) {
    this.player = player;
    this.src = src;
    this.dest = dest;
    this.level = level;
    this.numUnit = num;
  }

  abstract public void execute(Board b);

  abstract public boolean isValid(Board b);
}
