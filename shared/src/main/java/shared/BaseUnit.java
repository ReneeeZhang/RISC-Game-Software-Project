package shared;

import java.io.Serializable;

public class BaseUnit implements Unit, Upgradable, Serializable {
  int level;
  // for serialization
  private static final long serialVersionUID = 19407245;

  public BaseUnit() {
    this.level = 0;
  }

  public void upgrade() {
    this.level++;
  }

  public void upgradeTo(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }
}
