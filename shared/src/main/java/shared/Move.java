package shared;

import java.io.Serializable;
import shared.checkers.*;

public class Move extends R2RInstruction implements Serializable {
  private static final long serialVersionUID = 923749345;
  
  public Move(String s, String d, int n) {
    super(s, d, n);
  }

  @Override
  public void execute(Board b) {
    b.move(src, dest, numUnit);
    System.out.println(b);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Move: move ").append(numUnit).append(" unit(s) from ").append(src).append(" to ").append(dest)
        .append(".\n");
    return sb.toString();
  }
  
}
