package shared;

import java.util.List;

public interface Board {
  public List<Region> getNeighbor(String name);

  public Region getRegion(String name);

  public List<Region> getAllRegions();

  public void move(String src, String dst, int num);

  public void dispatch(String src, String dst, int num);
  
  public void attack(String src, String dst, int num);
}
