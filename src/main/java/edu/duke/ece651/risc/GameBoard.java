package edu.duke.ece651.risc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard implements Board, Drawable, Serializable {
  private Map<Region, List<Region>> regionMap;
  private static final long serialVersionUID = 12367648;
  
  public GameBoard() {
    this.regionMap = new HashMap<Region, List<Region>>();
  }
  
  public GameBoard(Map<Region, List<Region>> regionMap){
    this.regionMap = regionMap;
  }
  
  public List<Region> getNeighbor(String name) {
    for (Region r : regionMap.keySet()) {
      if (r.getName().equals(name)) {
        return regionMap.get(r);
      }
    }
    // TODO: NOT FOUND
    return new ArrayList<Region>();
  }
  // draw()
  @Override
  public String toString() {
    return "Success";
  }
}
