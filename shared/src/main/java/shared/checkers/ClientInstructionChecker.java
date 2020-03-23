package shared.checkers;

import shared.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClientInstructionChecker is to check all the instructions of one client.
 * Some instructions might be affected by former ones. Assume adjacent, accessible and
 * quantity checks have already been conducted.
 */

public class ClientInstructionChecker implements Checker{
    List<Instruction> instructions;
    Checker next;

    public ClientInstructionChecker(List<Instruction> instructions) {
        this(instructions, null);
    }

    public ClientInstructionChecker(List<Instruction> instructions, Checker next) {
        this.instructions = instructions;
        instructions.sort((o1, o2) -> {
            if (!o1.getClass().equals(o2.getClass())) {
                if (o1 instanceof Move) {
                    return -1;
                } else {
                    return 1;
                }
            }
            return 0;
        });
        this.next = next;
    }

    public void setNext(Checker next) {
        this.next = next;
    }
    @Override
    public boolean isValid() {
        Map<String, Integer> units = new HashMap<>();
        for (Instruction instruction : instructions) {
            R2RInstruction ins = (R2RInstruction) instruction;
            Region src = ins.getSrc();
            String srcName = src.getName();
            Region dest = ins.getDest();
            String destName = dest.getName();
            if (!units.containsKey(srcName)) {
                units.put(srcName, src.getNumBaseUnit());
            }
            if (!units.containsKey(destName)) {
                units.put(destName, dest.getNumBaseUnit());
            }
            int numUnit = ins.getNumUnit();
            int srcUnits = units.get(srcName);
            int destUnits = units.get(destName);
            if (srcUnits >= numUnit) {
                units.put(srcName, srcUnits - numUnit);
                if (ins instanceof Move) {
                    //if move, add units to dest
                    units.put(destName, destUnits + numUnit);
                }
            } else {
                if (ins instanceof Move) {
                    System.out.println("Move failed because units are not abundant after former steps. Source: " + srcName + ", Destination: " + destName);
                } else {
                    System.out.println("Attack failed because units are not abundant after former steps. Source: " + srcName + ", Destination: " + destName);
                }
                return false;
            }
        }
        return next == null || next.isValid();
    }
}
