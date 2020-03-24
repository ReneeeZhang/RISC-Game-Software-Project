package shared;

import java.util.List;

public interface Region {
  public String getName();

  public String getOwner();

  public String getColor();
  
  public List<Unit> sendUnit(int num);

  public void receiveUnit(List<Unit> toReceive);

  public void removeUnit(int num);

  public void removeUnit();
  
  public int getNumBaseUnit();

  public void setOwner(String owner);

  public void dispatch(String adjDest, int num);

  public List<Unit> getBorderCamp(String dest);

  public void initOneBorderCamp(String neighbor);
  
  public void autoIncrement();

}
