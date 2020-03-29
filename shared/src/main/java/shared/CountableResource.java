package shared;

public class CountableResource implements Resource {
  protected String name;
  protected int num;
  
  public CountableResource(String name, int num) {
    this.name = name;
    this.num = num;
  }

  public String getName() {
    return name;
  }

  public int getNum() {
    return num;
  }

  public void increase(int n) {
    num += n;
  }

  public void decrease(int n) {
    num -= n;
  }
}
