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
  private Map<String, List<Region>> playerRegionMap;
  private Map<String, Player> playerNameMap;
  private UpgradeLookup lookUp;

  public GameBoard() {
    this.regionMap = new HashMap<>();
    this.regionNameMap = new HashMap<>();
    this.playerRegionMap = new HashMap<>();
    this.playerNameMap = new HashMap<>();
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
    Player ally = p.getAlly();
    Map<Region, Integer> dist = new HashMap<Region, Integer>();
    // region : shortest distance
    initDistance(dist, player, src);
    if (ally != null && ally.getAlly().equals(p)) {
      initDistance(dist, ally.getName(), src);
    }
    getShortestDistance(dist, srcRegion, p, ally);
    return dist.get(dstRegion);
  }

  private void initDistance(Map<Region, Integer> dist, String player, String src) {
    for (Region r : playerRegionMap.get(player)) {
      if (r.getName().equals(src)) {
        dist.put(r, 0);
      } else {
        dist.put(r, 1000);
      }
    }
  }

  private void getShortestDistance(Map<Region, Integer> dist, Region srcRegion, Player p, Player ally) {
    // DFS
    List<Region> stack = new ArrayList<Region>();
    Set<Region> visited = new HashSet<Region>();
    stack.add(srcRegion);
    while (stack.size() > 0) {
      Region curr = stack.remove(0);
      // if not visited
      if (!visited.contains(curr)) {
        visited.add(curr);
        for (Region r : regionMap.get(curr)) {
          // if region's owner is player or player's ally
          if (r.getOwner().equals(p) || (ally != null && r.getOwner().equals(ally))) {
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
    Region srcRegion = getRegion(src);
    Region dstRegion = getRegion(dst);
    Player p = getPlayer(player);
    // if attack ally's region
    if (p.getAlly() != null
        && p.getAlly().equals(dstRegion.getOwner())) {
      p.breakAlly();
    }
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
  public void supportFood(String supportor, String supportee, int amount) {
    Player _supportor = getPlayer(supportor);
    Player _supportee = getPlayer(supportee);
    _supportor.decreaseFood(amount);
    _supportee.increaseFood(amount);
  }

  @Override
  public void inciteDefection(String src, String dst) {
    Region srcRegion = getRegion(src);
    Region dstRegion = getRegion(dst);
    List<BaseUnit> allUnits= dstRegion.getDefenseTroop();
    Collections.sort(allUnits);
    int len = allUnits.size();
    List<BaseUnit> traitors = new ArrayList<BaseUnit>();
    // defect half of the dest region's units
    for (int i = 0; i < len / 2; i++) {
      BaseUnit traitor = (allUnits.remove(0));
      traitor.setOwner(srcRegion.getOwner());
      traitors.add(traitor);
    }
    srcRegion.receiveUnit(traitors);
    dstRegion.receiveUnit(allUnits);
  }
  
  @Override
  public void resolveAlly() {
    for (Player p : playerNameMap.values()) {
      if (p.getAlly() != null) {
        // if p.ally has no ally or not p
        if (p.getAlly().getAlly() == null || !p.getAlly().getAlly().equals(p)) {
          // break all unilateral relations
          p.breakAlly();
        }
      }
    }
    for (Player p : playerNameMap.values()) {
      
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

  // for test only
  @Override
  public String toString() {
    String str = "";
    for (String player: playerRegionMap.keySet()) {
      str += player + " food: " + getPlayer(player).getFoodAmount() + ":\n-----------\n";
      for (Region r: playerRegionMap.get(player)) {
        String name = r.getName();
        str += " " + name + ":" + r.getAllUnitsAmount();
      }
      str += "\n";
    }
    return str;
  }
}
