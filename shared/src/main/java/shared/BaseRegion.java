package shared;

import java.io.Serializable;
import java.util.List;

public class BaseRegion implements Region, Serializable {
  private static final long serialVersionUID = 989463821;
  private String name;
  private String color;
  private List<Unit> units;

  //public BaseRegion(){}
  
  public BaseRegion(String name, String color, List<Unit> units){
    this.name = name;
    this.color = color;
    this.units = units;
  }

  public String getName() {
    return this.name;
  }
  
  /*
  public int getNumUnit() {
  }
  public String getOwner() {
  }
  public int setNumUnit() {
  }
  public String setOwner(String s){
  }
  */
}
