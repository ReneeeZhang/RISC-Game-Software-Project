package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard implements Board, Drawable, Serializable {
  private Map<Region, List<Region>> regionMap;
  private Map<String, Region> regionNameMap;
  // for serialization
  private static final long serialVersionUID = 12367648;
  
  public GameBoard() {
    this.regionMap = new HashMap<Region, List<Region>>();
    this.regionNameMap = new HashMap<String, Region>();
  }
  
  public GameBoard(Map<Region, List<Region>> regionMap){
    this.regionMap = regionMap;
    this.regionNameMap = new HashMap<String, Region>();
    for (Region r : regionMap.keySet()) {
      regionNameMap.put(r.getName(), r);
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
  public void move(String src, String dst) {
    Region srcRegion = regionNameMap.get(src);
    Region dstRegion = regionNameMap.get(dst);
    //TODO: region.recv and region.send
    //dstRegion.recv(srcRegion.send());
  }
  
  // draw()
  @Override
  public String toString() {
    String str = "";
    for (String n : regionNameMap.keySet()) {
      str += n + "(next to : ";
      for(Region r: regionMap.get(regionNameMap.get(n))){
        str += r.getName() + ", ";
      }
      str += ")\n";
    }
    return str;
  }
}
