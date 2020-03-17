package shared;

import java.util.List;

public interface Board {
  public List<Region> getNeighbor(String name);

  public Region getRegion(String name);
}
