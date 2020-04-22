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
      Thread.sleep(100);
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 7777));
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      // player num
      serial.writeObject(2);
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      // name
      deserial.readObject();
      while (input.hasNext()) {
        deserial = new ObjectInputStream(s.getInputStream());
        GameBoard b = (GameBoard) deserial.readObject();
        System.out.println(b);
        serial = new ObjectOutputStream(s.getOutputStream());
        Instruction move = new Move(input.next(), input.next(), input.next(), input.nextInt(), input.nextInt());
        Instruction attack = new Attack(input.next(), input.next(), input.next(), input.nextInt(), input.nextInt());
        List<Instruction> ins = new ArrayList<Instruction>();
        ins.add(move);
        ins.add(attack);
        serial.writeObject(ins);
      }
      sc.close();
    } catch (Exception e) {
    }
  }
}
