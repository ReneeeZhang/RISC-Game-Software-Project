package shared;
import java.io.Serializable;

public class Technology extends Resource implements Serializable{

  private static final long serialVersionUID = 923749376;
  
  public Technology(int amount) {
    super(amount);
  }
}
