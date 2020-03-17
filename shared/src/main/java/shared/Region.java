package shared;

public interface Region {
  public String getName();
  /*
  public int getNumUnit();
  public String getOwner();
  public int setNumUnit();
  public String setOwner(String s);
  */
  public void addUnit(int n);
  public void removeUnit(int n);
}
