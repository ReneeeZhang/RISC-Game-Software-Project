package shared;

import java.io.Serializable;
import java.util.ArrayList;
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
  
  public List<Unit> sendUnit(int num) {
    List<Unit> toSend = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      toSend.add(units.get(i));
      units.remove(i);
    }
    return toSend;
  }

  public void receiveUnit(List<Unit> toReceive) {
    for (Unit unit : toReceive) {
      units.add(unit);
    }
  }
   
  /*
  public int getNumUnit() {
  }
  public int setNumUnit() {
  }
  public String setOwner(String s){
  }
  */
  
}
