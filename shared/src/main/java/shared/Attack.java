package shared;

public class Attack extends R2RInstruction {
  public Attack(String s, String d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    if(isValid()) {
      //b.attack(src, dest)
    }
  }

  @Override
  public boolean isValid() {
    Checker c = new Checker(src, dest);
    return c.isValid();
  }
}
