package shared;

public class BaseUnit implements Unit {
  private String name;

  public BaseUnit(String name){
    this.name = name;
  }

  @Override
  public String getName(){
    return name;
  }
}
