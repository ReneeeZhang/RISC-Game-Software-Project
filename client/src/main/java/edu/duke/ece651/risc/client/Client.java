package edu.duke.ece651.risc.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shared.Attack;
import shared.Board;
import shared.Instruction;
import shared.Move;

public class Client {
  private Socket s;
  private Scanner scanner;
  
  public Client(String hostname, int port) throws IOException {
    SocketChannel sc = SocketChannel.open();
    sc.connect(new InetSocketAddress(hostname, port));
    this.s = sc.socket();
    scanner = new Scanner(System.in);
  }
  
  private Board receiveFromServer() throws IOException, ClassNotFoundException {
    DataInputStream din = new DataInputStream(s.getInputStream());
    ObjectInputStream deserial = new ObjectInputStream(din);
    return (Board) deserial.readObject();
  }
  
  private void sendToServer(Instruction inst) throws IOException {
    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
    ObjectOutputStream serial = new ObjectOutputStream(dout);
    serial.writeObject(inst);
  }

  private void sendToServer(List<Instruction> insts) throws IOException {
    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
    ObjectOutputStream serial = new ObjectOutputStream(dout);
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
      System.out.println("Wrong instruction! You can input one of: \"move\", \"attack|\"or \"done\".");
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
    System.out.println("Please input your instrution:");
    while (true) {
      String inst = scanner.nextLine();
      inst = inst.toLowerCase();
      if (inst.trim().equals("done")) {
        System.out.println("Finish inputting. Instruction(s) commited.");
        return insts;
      }
      Instruction r2rinst = generateInst(inst);
      // TODO: isValid should take in an argv
      if (!r2rinst.isValid()) {
        System.out.println("Please reinput your instruction:");
        continue;
      } else {
        insts.add(r2rinst);
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
    System.out.println("Illogical combination of your instructions. Please reinput all your instructions again.");
    return false;
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

  public void run() {
    try {
      while (true) {
        // receive the board from GameMaster
        Board board = receiveFromServer();
        // integrate all the instructions that a user input
        List<Instruction> packedInsts = packInsts(board);
        // send those packed instructions to server
        sendToServer(packedInsts);
      }
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException e) {
      System.out.println();
    }
  }
  
  public static void main(String[] args) {
    try {
      Client client = new Client(args[0], Integer.valueOf(args[1]));
      System.out.println("Connected");
      client.receiveFromServer();
      // Hardcode an instruction
      Instruction inst = new Move("Fitzpatrick", "Teer", 1);
      client.sendToServer(inst);
      client.receiveFromServer();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
