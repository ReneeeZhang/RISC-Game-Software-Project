package shared;

import java.io.Serializable;

public abstract class Resource implements Countable, Serializable {
  private static final long serialVersionUID = 923749377;
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
