package shared;

import java.util.List;

public interface Region {
  public String getName();

  public Player getOwner();

  public int getSize();

  public int getResourceProduction();

  public String getInfo();
  
  public List<BaseUnit> sendUnit(int level, int num);

  public List<BaseUnit> sendUnit(Player whoOwns, int level, int num);
  
  public void receiveUnit(List<BaseUnit> toReceive);

  public int getAllUnitsAmount();

  public void setOwner(Player owner);

  public void dispatch(String adjDest, int level, int num);

  public void dispatch(String adjDest, Player whoOwns, int level, int num);
  
  public List<BaseUnit> getDefenseTroop();
  
  public List<BaseUnit> getBorderCamp(String dest);

  public void initOneBorderCamp(String neighbor);
  
  public void autoIncrement();

  public void upgradeUnit(int oldLevel, int newLevel, int numUnit);

  public int numUnitWithLevel(int level);

  public int numUnitWithLevel(Player whoOwns, int level);
}
