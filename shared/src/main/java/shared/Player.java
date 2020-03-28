package shared;

public class Player {
  private String name;
  private FoodResource food;
  private TechResource tech;
  private int level;

  public Player(String name) {
    this.name = name;
    // TODO: how many when init
    this.food = new FoodResource(0);
    this.tech = new TechResource(0);
    this.level = 0;
  }

  public String getName() {
    return name;
  }

  public int getFood() {
    return food.getNum();
  }

  public int getTech() {
    return tech.getNum();
  }
  
  public int getLevel() {
    return level;
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
  
  public void upgrade(int n) {
    level += n;
  }
}
