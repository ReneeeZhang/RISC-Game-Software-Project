package shared;

import java.io.Serializable;

public class Player implements Upgradable, Serializable {
  private String name;
  private Food food;
  private Technology tech;
  private int techLevel;

  private static final long serialVersionUID = 923749365;

  public Player(String name) {
    this.name = name;
    // TODO: how many when init
    this.food = new Food(0);
    this.tech = new Technology(0);
    this.techLevel = 1;
  }

  public String getName() {
    return name;
  }

  public int getFoodAmount() {
    return food.getAmount();
  }

  public int getTechAmount() {
    return tech.getAmount();
  }

  public int getCurrLevel() {
    return techLevel;
  }

  public void decreaseFood(int n) {
    food.decrease(n);
  }

  public void decreaseTech(int n) {
    tech.decrease(n);
  }

  public void increaseFood(int n) {
    food.increase(n);
  }

  public void increaseTech(int n) {
    tech.increase(n);
  }

  @Override
  public void upgrade() {
    techLevel++;
  }

  @Override
  public void upgradeTo(int n) {
    return;
  }
}
