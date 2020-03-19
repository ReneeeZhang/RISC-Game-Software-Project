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
    src = s;
    dest = d;
    numUnit = n;
  }
  
  public void execute(Board b) {
    this.board = b;
    return;
  }

  public boolean isValid() {
    return true;
  }
}
