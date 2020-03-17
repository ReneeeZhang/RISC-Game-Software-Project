package shared;

public interface Region {
  public String getName();

  public String getOwner();

  public String getColor();
  
  public void addUnit(Unit unit);

  public void removeUnit(Unit unit);

  /*
  public int getNumUnit();
  public int setNumUnit();
  public String setOwner(String s);
  */
}
