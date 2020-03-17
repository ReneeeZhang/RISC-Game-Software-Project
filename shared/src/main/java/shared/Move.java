package shared;

public class Move extends R2RInstruction {
  public Move(String s, String d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    if(isValid()) {
      b.move(src, dest);
    }
  }

  @Override
  public boolean isValid() {
    Checker c = new Checker(src, dest);
    return c.isValid();
  }
}
