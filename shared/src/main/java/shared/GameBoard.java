package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GameBoard implements Board, Drawable, Serializable {
  private Map<Region, List<Region>> regionMap;
  private Map<String, Region> regionNameMap;
  private Map<String, List<Region>> playerRegionMap;
  // for serialization
  private static final long serialVersionUID = 12367648;
  
  public GameBoard() {
    this.regionMap = new HashMap<Region, List<Region>>();
    this.regionNameMap = new HashMap<String, Region>();
    this.playerRegionMap = new HashMap<String, List<Region>>();
  }

  public GameBoard(Map<Region, List<Region>> regionMap) {
    this.regionMap = regionMap;
    this.regionNameMap = new HashMap<String, Region>();
    this.playerRegionMap = new HashMap<String, List<Region>>();
    for (Region r : regionMap.keySet()) {
      regionNameMap.put(r.getName(), r);
      // if not exist
      if (playerRegionMap.containsKey(r.getOwner())) {
        playerRegionMap.get(r.getOwner()).add(r);
      } else {
        List<Region> regionList = new ArrayList<Region>();
        regionList.add(r);
        playerRegionMap.put(r.getOwner(), regionList);
      }
    }
  }

  public GameBoard(Map<Region, List<Region>> regionMap, Map<String, Region> regionNameMap, Map<String, List<Region>> playerRegionMap) {
    this.regionMap = regionMap;
    this.regionNameMap = regionNameMap;
    this.playerRegionMap = playerRegionMap;
  }

  @Override
  public List<Region> getNeighbor(String name) {
    Region r = regionNameMap.get(name);
    return regionMap.get(r);
  }

  @Override
  public Region getRegion(String name) {
    return regionNameMap.get(name);
  }

  @Override
  public Set<String> getAllRegionNames() {
    return new HashSet<String>(regionNameMap.keySet());
  }
  
  @Override
  public List<Region> getAllRegions() {
    return new ArrayList<Region>(regionNameMap.values());
  }

  @Override
  public void move(String src, String dst, int num) {
    Region srcRegion = regionNameMap.get(src);
    Region dstRegion = regionNameMap.get(dst);
    dstRegion.receiveUnit(srcRegion.sendUnit(num));
  }

  @Override
  public void attack(String src, String dst, int num) {
    Region srcRegion = regionNameMap.get(src);
    srcRegion.dispatch(dst, num);
  }
  
  @Override
  public void resolve() {
    for (String player : playerRegionMap.keySet()) {
      for (Region srcRegion : playerRegionMap.get(player)) {
        for (Region dstRegion : regionMap.get(srcRegion)) {
          fightAgainst(srcRegion, dstRegion);
        }
      }
    }
  }

  private boolean fightAgainst(Region src, Region dst) {
    List<Unit> units = src.getBorderCamp(dst.getName());
    while (units.size() > 0 && dst.getNumBaseUnit() > 0) {
      Random rand = new Random();
      int randA = rand.nextInt(20);
      int randB = rand.nextInt(20);
      if (randA > randB) {
        dst.removeUnit(1);
      } else {
        units.remove(0);
      }
    }
    return units.size() > 0;
  }

  @Override
  public String draw() {
    String str = "";
    for (String player : playerRegionMap.keySet()) {
      str += player + ":\n----------\n";
      for (Region r : playerRegionMap.get(player)) {
        String name = r.getName();
        str += " " + r.getNumBaseUnit() + " unit(s) in " + name;
        str += "(next to : ";
        for (Region neigh : regionMap.get(r)) {
          str += neigh.getName() + ", ";
        }
        str = str.substring(0, str.length() - 2);
        str += ")\n";
      }
    }
    return str;
  }
}
