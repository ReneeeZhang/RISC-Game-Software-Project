package shared;

public class TechResource implements Resource{
  private int num;
  
  public TechResource() {
    this.num = 0;
  }
  
  public int getNum() {
    return num;
  }

  public void increase(int n) {
    num += n;
  }
}
