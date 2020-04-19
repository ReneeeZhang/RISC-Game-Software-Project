package shared;

import java.io.Serializable;

public class Player implements Upgradable, Serializable {
  private String name;
  private Resource food;
  private Resource tech;
  private int techLevel;
  private Player ally;

  private static final long serialVersionUID = 923749365;

  public Player(String name) {
    this.name = name;
    this.food = new Food(50);
    this.tech = new Technology(50);
    this.techLevel = 1;
    this.ally = null;
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

  @Override
  public int getCurrLevel() {
    return techLevel;
  }

  public Player getAlly() {
    return ally;
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

  public void allyWith(Player that) {
    this.ally = that;
  }

  public void breakAlly() {
    this.ally = null;
  }

  @Override
  public void upgrade() {
    techLevel++;
  }

  @Override
  public void upgradeTo(int n) throws NoSuchMethodException{
    throw new NoSuchMethodException("Technology can only be upgraded once a level.");
  }

  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (that == null || !(that instanceof Player)) {
      return false;
    }

    Player thatPlayer = (Player) that;
    return this.name.equals(thatPlayer.getName());
  }
}
