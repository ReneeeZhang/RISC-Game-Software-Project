package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import shared.Board;
import shared.GameBoard;
import shared.instructions.*;

import shared.checkers.Checker;
import shared.checkers.ClientInstructionChecker;
import shared.checkers.LoserChecker;
import shared.checkers.WinnerChecker;

public class Client {
  private Socket s;
  private Scanner scanner;
  private String name;

  public Client(String hostname, int port) throws IOException {
    SocketChannel sc = SocketChannel.open();
    sc.connect(new InetSocketAddress(hostname, port));
    this.s = sc.socket();
    try{
      this.name = receiveNameFromServer();
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
    scanner = new Scanner(System.in);
  }

  private String receiveNameFromServer() throws IOException, ClassNotFoundException {
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    return (String) deserial.readObject();
  }
  
  private GameBoard receiveFromServer() throws IOException, ClassNotFoundException {
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    return (GameBoard) deserial.readObject();
  }

  private void sendToServer(List<Instruction> insts) throws IOException {
    ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
    serial.writeObject(insts);
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
  private List<Instruction> inputInst(Board board) {
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
        insts.add(realInst);
      }
      System.out.println("Instruction recorded.\nPlease input your next instruction:");
    }
  }

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
   */
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

  private boolean isOver(Board board) {
    if (hasWon(board) || hasLost(board)) {
      return true;
    }
    return false;
  }
  
  public void run() {
    try {
      while (true) {
        // receive the board from GameMaster
        GameBoard board = receiveFromServer();
        if (isOver(board)) {
          System.out.println("Game over~");
          return;
        }
        System.out.println(board.draw());
        // integrate all the instructions that a user input
        List<Instruction> packedInsts = packInsts(board);
        // send those packed instructions to server
        sendToServer(packedInsts);
      }
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }
  
  public static void main(String[] args) {
    try {
      Client client = new Client(args[0], Integer.valueOf(args[1]));
      System.out.println("Connected");
      client.run();
      /*
      client.receiveFromServer();
      // Hardcode an instruction
      Instruction inst = new Move("Fitzpatrick", "Teer", 1);
      client.sendToServer(inst);
      client.receiveFromServer();
      */
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
