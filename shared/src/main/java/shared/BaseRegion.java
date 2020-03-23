package shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseRegion implements Region, Serializable {
  private static final long serialVersionUID = 989463821;
  private String name;
  private String owner;
  private String color;
  private List<Unit> majorCamp; // For moving and defensing
  private Map<String, List<Unit>> borderCamps;
  
  public BaseRegion(String name, String owner, String color, List<Unit> majorCamp,
      Map<String, List<Unit>> borderCamps) {
    this.name = name;
    this.owner = owner;
    this.color = color;
    this.majorCamp = majorCamp;
    this.borderCamps = borderCamps;
  }

  public BaseRegion(String name, String owner) {
    this.name = name;
    this.owner = owner;
    this.color = "";
    this.majorCamp = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      majorCamp.add(new BaseUnit());
    }
    this.borderCamps = new HashMap<>();
  }

  /* // Copy constructor
  public BaseRegion(BaseRegion that) {
    this.name = new String(that.name);
    this.owner = new String(that.owner);
    this.color = new String(that.color);
    this.majorCamp = deepCopy(that.majorCamp);
    this.boarderCamps = deepCopy(that.boarderCamps);
  }

  private List<Unit> deepCopy(List<Unit> that) {
    List<Unit> copy = new ArrayList<>();
    for (Unit unit : that) {
      Unit copiedUnit = new BaseUnit(unit.getName());
      copy.add(copiedUnit);
    }
    return copy;
  }

  private Map<String, List<Unit>> deepCopy(Map<String, List<Unit>> that) {
    Map<String, List<Unit>> copy = new HashMap<>();
    for (String dest : that.keySet()) {
      List<Unit> copiedBoarderCamp = deepCopy(that.get(dest));
      copy.put(dest, copiedBoarderCamp);
    }
    return copy;
  }
  */

  public String getName() {
    return this.name;
  }

  public String getOwner() {
    return this.owner;
  }

  public String getColor() {
    return this.color;
  }

  public int getNumBaseUnit() {
    // TODO: Better way is to iterate the list of units and count all base units
    return majorCamp.size();
  }

  public List<Unit> sendUnit(int num) {
    List<Unit> toSend = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      toSend.add(majorCamp.remove(majorCamp.size()  - 1));
    }
    return toSend;
  }

  public void receiveUnit(List<Unit> toReceive) {
    for (Unit unit : toReceive) {
      majorCamp.add(unit);
    }
  }

  // TODO: change method name -> loseDefenser 
  public void removeUnit(int num) {
    for (int i = 0; i < num; i++) {
      majorCamp.remove(majorCamp.size() - 1);
    }
  }

  public void removeUnit() {
    majorCamp.remove(majorCamp.size() - 1);
  }
  
  public void setOwner(String owner) {
    this.owner = owner;
  }

  public void dispatch(String adjDest, int num) {
    List<Unit> boarderCamp = borderCamps.get(adjDest);
    for (int i = 0; i < num; i++) {
      boarderCamp.add(majorCamp.remove(majorCamp.size() - 1));
    }
  }

  public List<Unit> getBorderCamp(String dest) {
    List<Unit> troop = borderCamps.get(dest);
    borderCamps.replace(dest, new ArrayList<>());
    return troop;
  }

  public void initBorderCamp(String neighbor) {
    borderCamps.put(neighbor, new ArrayList<>());
  }
  
  public void autoIncrement(){
    majorCamp.add(new BaseUnit());
  }
}
