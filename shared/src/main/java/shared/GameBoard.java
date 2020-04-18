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

public class GameBoard implements Board, Serializable {
  private static final long serialVersionUID = 12367648;
  private Map<Region, List<Region>> regionMap;
  private Map<String, Region> regionNameMap;
  // TODO: move list<region> into player object
  private Map<String, List<Region>> playerRegionMap;
  private Map<String, Player> playerNameMap;
  private UpgradeLookup lookUp;

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
  public List<Region> getAllRegions(String owner) {
    return playerRegionMap.get(owner);
  }
  
  @Override
  public Set<String> getAllOwners() {
    return playerRegionMap.keySet();
  }

  @Override
  public int getDistance(String player, String src, String dst) {
    Region srcRegion = getRegion(src);
    Region dstRegion = getRegion(dst);
    Player p = getPlayer(player);
    List<Region> stack = new ArrayList<Region>();
    Player owner = getRegion(src).getOwner();
    Set<Region> visited = new HashSet<Region>();
    Map<Region, Integer> dist = new HashMap<Region, Integer>();
    // region : shortest distance
    for (Region r : playerRegionMap.get(owner.getName())) {
      if (r.getName().equals(src)) {
        dist.put(r, 0);
      } else {
        dist.put(r, 1000);
      }
    }
    // DFS
    stack.add(srcRegion);
    while (stack.size() > 0) {
      Region curr = stack.remove(0);
      // if not visited and under the same owner's control
      if (!visited.contains(curr) && curr.getOwner().equals(owner)) {
        visited.add(curr);
        for (Region r : regionMap.get(curr)) {
          // TODO: getAlly
          if (r.getOwner().equals(owner)) {
            stack.add(r);
            int cost = dist.get(curr) + curr.getSize();
            if (cost < dist.get(r)) {
              // update if it's shorter
              dist.put(r, cost);
            }
          }
        }
      }
    }
    return dist.get(dstRegion);
  }

  @Override
  public void move(String player, String src, String dst, int level, int num) {
    Region srcRegion = regionNameMap.get(src);
    Region dstRegion = regionNameMap.get(dst);
    Player p = getPlayer(player);
    // costs total size of regions * number of units moving
    p.decreaseFood(num * getDistance(player, src, dst));
    dstRegion.receiveUnit(srcRegion.sendUnit(p, level, num));
  }

  @Override
  public void attack(String player, String src, String dst, int level, int num) {
    Region srcRegion = regionNameMap.get(src);
    Player p = getPlayer(player);
    // costs 1 food per unit attacking
    p.decreaseFood(num);
    srcRegion.dispatch(dst, p, level, num);
  }

  @Override
  public void ally(String player1, String player2) {
    Player p1 = getPlayer(player1);
    Player p2 = getPlayer(player2);
    p1.allyWith(p2);
  }

  @Override
  public void resolveAlly() {
    for (Player p : playerNameMap.values()) {
      if (p.getAlly() != null) {
        // if p.ally has no ally or not p
        if (p.getAlly().getAlly() == null || !p.getAlly().getAlly().equals(p)) {
          p.breakAlly();
        }
      }
    }
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
      if (!playerRegionMap.containsKey(r.getOwner().getName())) {
        //TODO: might be deleted
        playerRegionMap.put(r.getOwner().getName(), new ArrayList<Region>());
      }
      playerRegionMap.get(r.getOwner().getName()).add(r);
    }
  }

  private void fight(Region src, Region dst) {
    List<BaseUnit> attackUnits = src.getBorderCamp(dst.getName());
    List<BaseUnit> defenseUnits = dst.getDefenseTroop();
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
      // Highest attack bonus vs lowest defense bonus
      if (round % 2 == 0) {
        unitA = attack.get(attack.size() - 1);
        unitB = defense.get(0);
      } else { // Lowest defense bonus vs highest attack bonus
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
}
