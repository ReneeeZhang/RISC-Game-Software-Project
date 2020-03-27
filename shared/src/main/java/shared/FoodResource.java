package shared;

public class FoodResource implements Resource{
  private int num;

  public FoodResource() {
    this.num = 0;
  }

  public int getNum() {
    return num;
  }

  public void increase(int n) {
    num += n;
  }
}
