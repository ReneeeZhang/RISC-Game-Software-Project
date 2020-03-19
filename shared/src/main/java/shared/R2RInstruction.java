package shared;

public class R2RInstruction implements Instruction {
  protected String src;
  protected String dest;
  protected int numUnit;
  protected Board board;

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
    return;
  }

  public boolean isValid() {
    return true;
  }
}
