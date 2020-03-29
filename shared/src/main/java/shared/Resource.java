package shared;

public abstract class Resource implements Countable {
  protected int amount;
  
  public Resource(int amount) {
    this.amount = amount;
  }

  public int getAmount() {
    return amount;
  }

  public void increase(int n) {
    amount += n;
  }

  public void decrease(int n) {
    amount -= n;
  }
}
