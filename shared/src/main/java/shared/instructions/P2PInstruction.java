package shared.instructions;

import java.io.Serializable;

import shared.Board;

abstract public class P2PInstruction implements Instruction, Serializable{
  private static final long serialVersionUID = 20391203;
  protected String player1;
  protected String player2;

  protected P2PInstruction(String player1, String player2) {
    this.player1 = player1;
    this.player2 = player2;
  }

  abstract public boolean isValid(Board b);

  abstract public void execute(Board b);
}
