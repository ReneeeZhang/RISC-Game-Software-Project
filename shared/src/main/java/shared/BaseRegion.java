package shared;

import java.io.Serializable;
import java.util.List;

public class BaseRegion implements Region, Serializable {
  private static final long serialVersionUID = 989463821;
  private String name;
  private String owner;
  private String color;
  private List<Unit> units;

  //public BaseRegion(){}
  
  public BaseRegion(String name, String owner, String color, List<Unit> units){
    this.name = name;
    this.owner = owner;
    this.color = color;
    this.units = units;
  }

  public String getName() {
    return this.name;
  }

  public String getOwner() {
    return this.owner;
  }

  public String getColor() {
    return this.color;
  }
  
  public void addUnit(Unit unit) {
    units.add(unit);
  }

  public void removeUnit(Unit unit) {
    //TODO: what if unit does belong to units
    units.remove(unit);
  }
   
  /*
  public int getNumUnit() {
  }
  public int setNumUnit() {
  }
  public String setOwner(String s){
  }
  */
  public void addUnit(int n) {
  }
  public void removeUnit(int n){
  }
  
}
