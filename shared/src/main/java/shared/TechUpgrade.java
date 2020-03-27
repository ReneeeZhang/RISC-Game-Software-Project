package shared;

import java.io.Serializable;
import shared.checkers.*;
import java.util.HashMap;

public class TechUpgrade extends UpgradeInstruction implements Serializable {
  private 
  private static final long serialVersionUID = 923749347;

  public TechUpgrade(String s, int l) {
    super(s, l);
  }

  @Override
  public void execute(Board b) {
    b.upgrade(src, level);
  }

  // @Override
  // public boolean isValid(Board b) {

  // }
}
