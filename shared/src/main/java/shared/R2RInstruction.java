package shared;

import java.io.Serializable;
import shared.checkers.*;

abstract public class R2RInstruction implements Instruction, Serializable {
  protected String src;
  protected String dest;
  protected int numUnit;
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
  
  //abstract void execute(Board b);
  //public boolean isValid(Board b);

  // Getters
  public String getSrc() {
    return src;
  }

  public String getDest() {
    return dest;
  }

  public int getNumUnit() {
    return numUnit;
  }
}