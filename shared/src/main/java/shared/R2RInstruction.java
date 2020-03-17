package shared;

public class R2RInstruction implements Instruction {
  protected String src;
  protected String dest;
  protected int numUnit;

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
    return;
  }

  public boolean isValid() {
    return true;
  }
}
