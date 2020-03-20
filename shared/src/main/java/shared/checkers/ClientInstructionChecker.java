package shared.checkers;

import shared.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientInstructionChecker implements Checker{
    List<Move> moves;
    List<Attack> attacks;
    Checker next;

    public ClientInstructionChecker(List<Instruction> instructions) {
        this(instructions, null);
    }

    public ClientInstructionChecker(List<Instruction> instructions, Checker next) {
        for (Instruction instruction : instructions) {
            if (instruction instanceof Move) {
                moves.add((Move) instruction);
            }
            if (instruction instanceof Attack) {
                attacks.add((Attack) instruction);
            }
        }
        this.next = next;
    }


    @Override
    public boolean isValid() {
        Map<String, Integer> units = new HashMap<>();
        for (Move move : moves) {
            //TODO getSource, get
//            String srcName = move;
        }


        return next.isValid();
    }
}
