package shared;

import java.util.HashMap;

public class UnitNameLookup {
  private HashMap<Integer, String> nameMap;

  public UnitNameLookup() {
    this.nameMap = new HashMap<Integer, String>();
    this.nameMap.put(0, "freshman");
    this.nameMap.put(1, "sophomore");
    this.nameMap.put(2, "junior");
    this.nameMap.put(3, "senior");
    this.nameMap.put(4, "master");
    this.nameMap.put(5, "phdCandidate");
    this.nameMap.put(6, "phd");
  }

  public String getName(int level) {
    return nameMap.get(level);
  }
}
