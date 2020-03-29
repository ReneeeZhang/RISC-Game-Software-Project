package shared.instructions;

import shared.*;

public interface Instruction {
  public void execute(Board b);
  public boolean isValid(Board b);
}
