package edu.duke.ece651.risc.server;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.instructions.*;

import org.junit.jupiter.api.Test;

public class GameMasterTest {
  @Test
  public void test_GameMaster() {
    GameMaster gm = new GameMaster(2);
    assertTrue(!gm.isFull());
    Map<SocketChannel, List<Instruction>> map = new HashMap<SocketChannel, List<Instruction>>();
    List<Instruction> ins = new ArrayList<Instruction>();
    Instruction move = new Move("Hudson", "Wilson", 0, 5);
    Instruction attack = new Attack("Bostock", "Wilson", 0, 3);
    Instruction techUpgrade = new TechUpgrade("Player1", 1, 2);
    Instruction unitUpgrade = new UnitUpgrade("Player1", "Fitzpatrick", 0, 2, 1);
    ins.add(move);
    ins.add(attack);
    ins.add(techUpgrade);
    ins.add(unitUpgrade);
    try{
      map.put(SocketChannel.open(), ins);
    } catch (Exception e) {
      System.out.println(e);
    }
    gm.executeAll(map);
    gm.autoIncrement();
  }

}
