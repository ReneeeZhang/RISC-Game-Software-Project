package shared;

import java.util.List;

public interface Board {
  public List<Region> getNeighbor(String name);

  public void move(String src, String dst);
}
