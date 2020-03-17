package shared;

public class Move extends R2RInstruction {
  public Move(Region s, Region d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    if(isValid()) {
      src.removeUnit(numUnit);
      dest.addUnit(numUnit);
    }
  }

  @Override
  public boolean isValid() {
    Checker c = new Checker(src, dest);
    return c.isValid();
  }
}
