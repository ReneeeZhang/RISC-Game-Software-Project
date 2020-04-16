package shared;

import java.util.List;
import java.util.Set;

public interface Board {
  public List<Region> getNeighbor(String name);

  public Region getRegion(String name);

  public Player getPlayer(String name);

  public Set<String> getAllRegionNames();

  public Set<String> getRegionNames(String owner);

  public List<Region> getAllRegions();

  public List<Region> getAllRegions(String owner);
  
  public Set<String> getAllOwners();

  public int getDistance(String player, String src, String dst);
  
  public void move(String player, String src, String dst, int level, int num);

  public void attack(String player, String src, String dst, int level, int num);

  public void ally(String player1, String player2);
  
  public void resolve();

  public void resolveAlly();
}
