package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
  private Map<String, Player> playerNameMap;
  private UpgradeLookup lookUp;
  // for serialization
  private static final long serialVersionUID = 12367648;
  
  public GameBoard() {
    this.regionMap = new HashMap<Region, List<Region>>();
    this.regionNameMap = new HashMap<String, Region>();
    this.playerRegionMap = new HashMap<String, List<Region>>();
    this.playerNameMap = new HashMap<String, Player>();
    this.lookUp = new UpgradeLookup();
  }

  public GameBoard(Map<Region, List<Region>> regionMap, Map<String, Region> regionNameMap,
      Map<String, Player> playerNamemap, Map<String, List<Region>> playerRegionMap) {
    this.regionMap = regionMap;
    this.regionNameMap = regionNameMap;
    this.playerRegionMap = playerRegionMap;
    this.playerNameMap = playerNamemap;
    this.lookUp = new UpgradeLookup();
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
  public Player getPlayer(String name) {
    return playerNameMap.get(name);
  }
  
  @Override
  public Set<String> getAllRegionNames() {
    return new HashSet<String>(regionNameMap.keySet());
  }

  @Override
  public Set<String> getRegionNames(String owner) {
    Set<String> ans = new HashSet<String>();
    for (Region r : playerRegionMap.get(owner)) {
      ans.add(r.getName());
    }
    return ans;
  }
  
  @Override
  public List<Region> getAllRegions() {
    return new ArrayList<Region>(regionNameMap.values());
  }

  @Override
  public Set<String> getAllOwners() {
    return playerRegionMap.keySet();
  }

  @Override
  public int getDistance(String src, String dst) {
    for (Region r : getNeighbor(src)) {
      //TODO:get shortest path
       
    }
    return 0;
  }
  
  @Override
  public void move(String src, String dst, int level, int num) {
    Region srcRegion = regionNameMap.get(src);
    Region dstRegion = regionNameMap.get(dst);
    dstRegion.receiveUnit(srcRegion.sendUnit(level, num));
  }

  @Override
  public void attack(String src, String dst, int level, int num) {
    Region srcRegion = regionNameMap.get(src);
    srcRegion.dispatch(dst, level, num);
  }
  
  @Override
  public void resolve() {
    for (String player : playerRegionMap.keySet()) {
      for (Region srcRegion : playerRegionMap.get(player)) {
        for (Region dstRegion : regionMap.get(srcRegion)) {
          fight(srcRegion, dstRegion);
        }
      }
    }
    // update player-region map
    playerRegionMap = new HashMap<String, List<Region>>();
    for (Region r : getAllRegions()) {
      if (!playerRegionMap.containsKey(r.getOwner())) {
        playerRegionMap.put(r.getOwner(), new ArrayList<Region>());
      }
      playerRegionMap.get(r.getOwner()).add(r);
    }
  }

  private void fight(Region src, Region dst) {
    List<BaseUnit> attackUnits = src.getBorderCamp(dst.getName());
    List<BaseUnit> defenseUnits = dst.getMajorCamp();
    Collections.sort(attackUnits);
    Collections.sort(defenseUnits);
    fight(attackUnits, defenseUnits);
    // if wins, change the owner and send the rest units
    if (attackUnits.size() > 0) {
      dst.setOwner(src.getOwner());
      dst.receiveUnit(attackUnits);
    }else{
      dst.receiveUnit(defenseUnits);
    }
  }

  private void fight(List<BaseUnit> attack, List<BaseUnit> defense){
    Random rand = new Random();
    int round = 0;
    while (attack.size() > 0 && defense.size() > 0) {
      int randA = rand.nextInt(20);
      int randB = rand.nextInt(20);
      BaseUnit unitA;
      BaseUnit unitB;
      // A15 vs D0
      if (round % 2 == 0) {
        unitA = attack.get(attack.size() - 1);
        unitB = defense.get(0);
      } else { // A0 vs D8
        unitA = attack.get(0);
        unitB = defense.get(defense.size() - 1);
      }
      int bonusA = lookUp.getBonus(unitA.getCurrLevel());
      int bonusB = lookUp.getBonus(unitB.getCurrLevel());
      if (randA + bonusA > randB + bonusB) {
        defense.remove(unitB);
      } else {
        attack.remove(unitA);
      }
      round++;
    }
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
