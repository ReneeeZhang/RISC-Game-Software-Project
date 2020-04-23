package edu.duke.ece651.risc.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import shared.GameBoard;
import shared.instructions.Attack;
import shared.instructions.Instruction;
import shared.instructions.Move;

public class FakeClient implements Runnable {
  private Scanner input;
  
  public FakeClient(String s) {
    this.input = new Scanner(s);
  }

  public void run() {
    try {
      Thread.sleep(50);
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 7777));
      Socket s = sc.socket();
      
      // send player number to server
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(2);
      
      // recv player name from server
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      String name = (String) deserial.readObject();

      // recv board from server
      deserial = new ObjectInputStream(s.getInputStream());
      GameBoard b = (GameBoard) deserial.readObject();
      
      Instruction move = new Move(input.next(), input.next(), input.next(), input.nextInt(), input.nextInt());
      Instruction attack = new Attack(input.next(), input.next(), input.next(), input.nextInt(), input.nextInt());
      List<Instruction> ins = new ArrayList<Instruction>();
      ins.add(move);
      ins.add(attack);

      Thread.sleep(500);
      // send instr to server
      serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(ins);
      //sc.close();
    } catch (Exception e) {
    }
  }
}
