package shared;

public class Technology extends Resource implements Upgradable {
  private int level;
  
  public Technology(int amount) {
    super(amount);
    this.level = 1;
  }

  public void upgrade() {
    level++;
  }

  public void upgradeTo(int level) {
    return;
  }

  public int getCurrLevel() {
    return level;
  }
}
