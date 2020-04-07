package shared;

import java.util.List;

public interface Region {
  public String getName();

  public String getOwner();

  public String getColor();

  public int getSize();

  public int getResourceProduction();

  public String getInfo();
  
  public List<BaseUnit> sendUnit(int num);

  public List<BaseUnit> sendUnit(int level, int num);

  public void receiveUnit(List<BaseUnit> toReceive);

  public void removeUnit(int num);

  public void removeUnit();
  
  public int getNumBaseUnit();

  public void setOwner(String owner);

  public void dispatch(String adjDest, int num);

  public void dispatch(String adjDest, int level, int num);

  public List<BaseUnit> getMajorCamp();
  
  public List<BaseUnit> getBorderCamp(String dest);

  public void initOneBorderCamp(String neighbor);
  
  public void autoIncrement();

  public void upgradeUnit(int oldLevel, int newLevel, int numUnit);

  public int numUnitWithLevel(int level);
}
