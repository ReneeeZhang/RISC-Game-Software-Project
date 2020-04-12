package shared;

public interface Upgradable {
  public void upgrade();

  public void upgradeTo(int level) throws NoSuchMethodException;

  public int getCurrLevel();
}
