package shared;

import java.io.Serializable;

public class BaseUnit implements Unit, Serializable {
  private String name;
  // for serialization
  private static final long serialVersionUID = 19407245;

  public BaseUnit(String name){
    this.name = name;
  }

  @Override
  public String getName(){
    return name;
  }
}
