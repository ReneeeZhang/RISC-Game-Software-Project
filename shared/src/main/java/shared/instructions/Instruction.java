package shared.instructions;

import shared.Board;

public interface Instruction {

  public void execute(Board b);

  public boolean isValid(Board b);
}
