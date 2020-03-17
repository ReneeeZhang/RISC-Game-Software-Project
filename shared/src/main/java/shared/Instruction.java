package shared;

public interface Instruction {
  public void execute(Board b);
  public boolean isValid();
}
