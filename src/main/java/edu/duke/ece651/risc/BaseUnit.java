package edu.duke.ece651.risc;

public class BaseUnit implements Unit {
  private String name;

  public BaseUnit(String name){
    this.name = name;
  }
  
  public String getName(){
    return name;
  }
}