package shared;

import java.util.List;

public interface Region {
  public String getName();

  public String getOwner();

  public String getColor();
  
  public List<BaseUnit> sendUnit(int num);

  public void receiveUnit(List<BaseUnit> toReceive);

  public void removeUnit(int num);

  public void removeUnit();
  
  public int getNumBaseUnit();

  public void setOwner(String owner);

  public void dispatch(String adjDest, int num);

  public List<BaseUnit> getBorderCamp(String dest);

  public void initOneBorderCamp(String neighbor);
  
  public void autoIncrement();

  public boolean hasUnitWithLevel(int level);
}
