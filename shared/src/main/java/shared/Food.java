package shared;

import java.io.Serializable;

public class Food extends Resource implements Serializable{

   private static final long serialVersionUID = 923749375;
  
  public Food(int num) {
    super(num);
  }
}
