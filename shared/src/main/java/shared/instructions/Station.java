package shared.instructions;

import shared.Board;

public class Station extends R2RInstruction {
  public static final long serialVerionUID = 5093499;

  public Station(String src, String dst, int level, int num) {
    super(src, dst, level, num);
  }

  public boolean isValid(Board b) {
    return true;
  }

  public void execute(Board b) {
    
  }
}
