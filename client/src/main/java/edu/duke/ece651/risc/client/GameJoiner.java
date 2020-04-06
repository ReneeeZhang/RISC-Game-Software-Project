package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import shared.Board;
import shared.GameBoard;
import shared.instructions.*;

import shared.checkers.Checker;
import shared.checkers.GameOverChecker;
import shared.checkers.LoserChecker;
import shared.checkers.WinnerChecker;

public class GameJoiner extends Connector implements Runnable{
  private Scanner scanner;
  private String name;
  //private List<SocketChannel> games;
  //private Map<SocketChannel, String> socketPlayerMap;

  public GameJoiner(String hostname, int port) throws IOException {
    super(hostname, port);
    scanner = new Scanner(System.in);
  }
  
  private boolean isValidInstName(String instName) {
    return instName.equals("move") || instName.equals("attack") || instName.equals("done");
  }
  
  private boolean canGenerateInst(String[] parts) {
    // Check instruction length
    if (parts.length != 4) {
      System.out.println(
          "Wrong instruction format.There are four parts delimitted by space(s) in your instruction.\nUsage:{INSTRUCTION} {SOURCE} {DESTINATION} {NUM}");
      return false;
    }
    // Check instruction name
    if (!isValidInstName(parts[0])) {
      System.out.println("Wrong instruction! You can input one of: \"move\", \"attack\"or \"done\".");
      return false;
    }
    // Check number
    try{
      Integer.parseInt(parts[3]);
    }
    catch (NumberFormatException e) {
      System.out.println("Wrong number format! Please input a number as the last element of your instruction.");
      return false;
    }
    return true;
  }
  
  private Instruction generateInst(String inst) {
    String[] parts = inst.split("\\s+");
    if (!canGenerateInst(parts)) {
      return null;
    }

    int num = Integer.parseInt(parts[3]);
    parts[0] = parts[0].toLowerCase();
    Instruction r2rinst = null;
    if (parts[0].equals("move")) {
      r2rinst = new Move(parts[1], parts[2], num);
    }
    else if (parts[0].equals("attack")) {
      r2rinst = new Attack(parts[1], parts[2], num);
    }
    return r2rinst;
  }

  /**
   * Return all the instructions (in the order of what the user input)
   * At the same time, it will also check each single instruction that the user input for validity  
   */
  private List<Instruction> collectInsts(Board board) {
    List<Instruction> insts = new ArrayList<>();
    System.out.println("You are " + name);
    System.out.println("Please input your instrution:");
    while (true) {
      String inst = scanner.nextLine();
      if (inst.toLowerCase().trim().equals("done")) {
        System.out.println("Finish inputting. Instruction(s) commited.");
        return insts;
      }
      Instruction realInst = generateInst(inst);
      if (realInst == null || !realInst.isValid(board)) {
        System.out.println("Please reinput your instruction:");
        continue;
      } else {
        realInst.execute(board);
        insts.add(realInst);
      }
      System.out.println("Instruction recorded.\nPlease input your next instruction:");
    }
  }
  /*
  private void sortInsts(List<Instruction> insts) {
    insts.sort((o1, o2) -> {
      if (!o1.getClass().equals(o2.getClass())) {
        if (o1 instanceof Move) {
          return -1;
        } else {
          return 1;
        }
      }
      return 0;
    });
  }
  
  /**
   * Simulate all the instructions. 
   * Return true if simulation results are valid;
   * Otherwise, return false  
   
  private boolean simulateAllInsts(List<Instruction> insts, Board board) {
    ClientInstructionChecker cic = new ClientInstructionChecker(board, insts);
    if (!cic.isValid()) {
      System.out.println("Illogical combination of your instructions. Please reinput all your instructions again.");
      return false;
    }
    return true;
  }
  
  // return a list of sorted instructions (move comes before attack)
  private List<Instruction> packInsts(Board board){
    while (true) {
      List<Instruction> insts = inputInst(board);
      sortInsts(insts);
      if (!simulateAllInsts(insts, board)) {
        continue;
      }
      return insts;
    }
  }
  */
  private boolean hasWon(Board board) {
    Checker winnerChecker = new WinnerChecker(board, name);
    if (winnerChecker.isValid()) {
      System.out.println(name + ", you have won!");
      return true;
    }
    return false;
  }

  private boolean hasLost(Board board) {
    Checker loserChecker = new LoserChecker(board, name);
    if (loserChecker.isValid()) {
      System.out.println(name + ", you have lost...");
      return true;
    }
    return false;
  }

  /**
   * Check game result based on given {@argv board}
   * Return true if this client is the winner
   * Return false if this client loses  
   */
  /*
  private void checkResult(GameBoard board) {
    if (hasWon(board)) {
      System.out.println("Game over~");
      return true;
    }    
  }
  */
  
  public void run() {
    try {
      //send(3); // want to join a game of 2. Send the number of players to server
      this.name = (String) receive();
      while (true) {
        // receive the board from GameMaster
        GameBoard board = (GameBoard)receive();
        if (hasWon(board)) {
          System.out.println("Game over~");
          return;
        }
        if (hasLost(board)) {
          System.out.println("Would you like to continue to watch the game? Please answer yes/no:");
          while (true) {
            String ans2Lost = scanner.nextLine().toLowerCase();

            if (ans2Lost.equals("yes")) {
              send(ans2Lost); // Send "yes" to server
              while (true) {
                GameBoard board2Watch = (GameBoard) receive();
                System.out.println(board2Watch.draw());
                GameOverChecker gmoChecker = new GameOverChecker(board2Watch);
                if (gmoChecker.isValid()) {
                  System.out.println("Game over~");
                  return;
                }
                else {
                  send(new ArrayList<Instruction>());
                }
              }
            }
            else if (ans2Lost.equals("no")) {
              send(ans2Lost);  // send "no" to server
              return;
            }
            else {
              System.out.println("You can only input yes or no.");
            }              
          }
        }
        System.out.println(board.draw());
        // integrate all the instructions that a user input
        List<Instruction> collectedInsts = collectInsts(board);
        // send those packed instructions to server
        send(collectedInsts);
      }
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }
}
