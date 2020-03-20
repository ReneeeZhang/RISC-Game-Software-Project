package shared;

import java.io.Serializable;

public class R2RInstruction implements Instruction, Serializable {
  protected String src;
  protected String dest;
  protected int numUnit;
  protected Board board;
  private static final long serialVersionUID = 435352123;

  // Default constructor
  public R2RInstruction() {
  }

  // Constructor
  public R2RInstruction(String s, String d, int n) {
    this.src = s;
    this.dest = d;
    this.numUnit = n;
  }
  
  public void execute(Board b) {
    this.board = b;
  }

  public boolean isValid() {
    return true;
  }

  // Getters
  public Region getSrc() {
    return board.getRegion(src);
  }

  public Region getDest() {
    return board.getRegion(dest);
  }

  public int getNumUnit() {
    return numUnit;
  }
}
