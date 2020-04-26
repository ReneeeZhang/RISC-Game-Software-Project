package shared.instructions;

import shared.Board;
import shared.Region;
import shared.checkers.AdjacentChecker;
import shared.checkers.InciteDefectionTargetChecker;

public class InciteDefection extends R2RInstruction{
  private static final long serialVersionUID = 354265972;

  public InciteDefection(String player, String src, String dest, int level, int num) {
    super(player, src, dest, level, num);
  }

  @Override
  public void execute(Board board) {
    // TODO: add such method in board

  }

  @Override
  public boolean isValid(Board board) {
    Region source = board.getRegion(src);
    Region destination = board.getRegion(dest);

    InciteDefectionTargetChecker inciteDefectionTargetchecker= new InciteDefectionTargetChecker(source.getOwner(), destination.getOwner());
    AdjacentChecker aChecker = new AdjacentChecker(board, source, destination, inciteDefectionTargetchecker);
    return aChecker.isValid();
  }
}
