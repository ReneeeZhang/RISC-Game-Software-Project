package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

  public GameBoard(Map<Region, List<Region>> regionMap){
    this.regionMap = regionMap;
    this.regionNameMap = new HashMap<String, Region>();
    this.playerRegionMap = new HashMap<String, List<Region>>();
    for (Region r : regionMap.keySet()) {
      regionNameMap.put(r.getName(), r);
      // if not exist
      if(playerRegionMap.containsKey(r.getOwner())){
        playerRegionMap.get(r.getOwner()).add(r);
      } else {
        List<Region> regionList = new ArrayList<Region>();
        regionList.add(r);
        playerRegionMap.put(r.getOwner(), regionList);
      }
    }
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
      for (Region region : playerRegionMap.get(player)) {
        for (Region target : regionMap.get(region)) {
          List<Unit> units = region.getBorderCamp(target.getName());
          if (fightAgainst(units, target)) {
            // wins the fight
            target.setOwner(player);
            target.receiveUnit(units);
          }
        }
      }
    }
  }

  private boolean fightAgainst(List<Unit> units, Region target) {
    while (units.size() > 0 && target.getNumBaseUnit() > 0) {
      Random rand = new Random();
      int randA = rand.nextInt(20);
      int randB = rand.nextInt(20);
      if (randA > randB) {
        target.removeUnit(1);
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
      str += player + ":\n----------\n ";
      for (Region r : playerRegionMap.get(player)) {
        String name = r.getName();
        str += r.getNumBaseUnit() + " unit(s) in " + name;
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
