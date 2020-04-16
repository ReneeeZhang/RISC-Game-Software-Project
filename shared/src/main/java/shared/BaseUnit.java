package shared;

import java.io.Serializable;

public class BaseUnit implements Unit, Upgradable, Serializable, Comparable<BaseUnit> {
  private Player owner;
  private int level;
  // for serialization
  private static final long serialVersionUID = 19407245;

  public BaseUnit(Player owner) {
    this.level = 0;
    this.owner = owner;
  }

  public Player getOwner() {
    return this.owner;
  }
  
  public void upgrade() {
    this.level++;
  }

  public void upgradeTo(int level) {
    this.level = level;
  }

  public int getCurrLevel() {
    return level;
  }

  public int compareTo(BaseUnit that) {
    if (this.getCurrLevel() < that.getCurrLevel()) {
      return -1;
    }
    else if (this.getCurrLevel() > that.getCurrLevel()) {
      return 1;
    }
    else {
      return 0;
    }
  }
}
