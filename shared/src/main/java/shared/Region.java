package shared;

import java.util.List;

public interface Region {
  public String getName();

  public String getOwner();

  public String getColor();
  
  public List<Unit> sendUnit(int num);

  public void receiveUnit(List<Unit> toReceive);

  public int getNumBaseUnit();
  /*
  public int setNumUnit();
  public String setOwner(String s);
  */
}
