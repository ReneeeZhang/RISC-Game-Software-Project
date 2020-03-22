package shared;
import java.io.Serializable;
import shared.checkers.*;

public class Attack extends R2RInstruction implements Serializable{
  private static final long serialVersionUID = 923749346;
  
  public Attack(String s, String d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    b.attack(src, dest, numUnit);
  }
}
