package edu.duke.ece651.risc.server;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import shared.instructions.Ally;
import shared.instructions.Attack;
import shared.instructions.InciteDefection;
import shared.instructions.Instruction;
import shared.instructions.Move;

public class ServerTest {
  @Test
  public void test_Server() {
    try {
      List<Instruction> instr1 = new ArrayList<>();
      List<Instruction> instr2 = new ArrayList<>();
      instr1.add(new Move("Player1", "Hudson", "Wilson", 0, 1));
      instr1.add(new Attack("Player1", "Wilson", "Teer", 0, 10));
      instr2.add(new Ally("Player2", "Player1"));
      instr2.add(new InciteDefection("Player2", "Perkins", "Fitzpatrick", 0, 0));
      // start fake clients
      Thread fc1 = new Thread(new FakeClient(instr1));
      Thread fc2 = new Thread(new FakeClient(instr2));
      Thread fc3 = new Thread(new FakeClient(new ArrayList<>()));
      fc1.start();
      fc2.start();
      // runserver
      Server server = Server.start("src/main/resources/config.txt");
      for (int i = 0; i < 2; i++) {
        server.handleRequest();
      }
      fc3.start();
      server.handleRequest();
      fc1.join();
      fc2.join();
    } catch (Exception e) {
    }
  }
}
