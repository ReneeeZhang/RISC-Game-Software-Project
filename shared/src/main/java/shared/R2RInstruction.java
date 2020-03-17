package shared;

public class R2RInstruction implements Instruction {
  protected Region src;
  protected Region dest;
  protected int numUnit;

  // Default constructor
  public R2RInstruction() {
  }

  // Constructor
  public R2RInstruction(Region s, Region d, int n) {
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
