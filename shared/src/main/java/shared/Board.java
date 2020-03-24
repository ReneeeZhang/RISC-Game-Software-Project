package shared;

import java.util.List;
import java.util.Set;

public interface Board {
  public List<Region> getNeighbor(String name);

  public Region getRegion(String name);

  public Set<String> getAllRegionNames();

  public List<Region> getAllRegions();

  public Set<String> getAllOwners();
  
  public void move(String src, String dst, int num);

  public void attack(String src, String dst, int num);
  
  public void resolve();
}
