package edu.duke.ece651.risc;

import java.util.List;

public class BaseRegion implements Region {
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