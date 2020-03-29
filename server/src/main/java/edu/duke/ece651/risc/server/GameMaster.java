package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import shared.*;
import shared.checkers.*;
import shared.instructions.*;

public class GameMaster implements Runnable {
  private Board board;
  private List<SocketChannel> playerSockets;

  public GameMaster(List<SocketChannel> playerSockets) {
    this.playerSockets = playerSockets;
    int num = playerSockets.size();
    try {
      Initializer initer = new Initializer(num);
      this.board = initer.initGame();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void run() {
    try{
      sendPlayerNames();
    } catch (IOException e) {
      System.out.println(e);
    }
    int cnt = 1;
    while (true) {
      System.out.println("Round " + cnt + " Starts!");
      try{
        sendBoardToClients();
        for (String player : board.getAllOwners()) {
          Checker winCheck = new WinnerChecker(board, player);
          if (winCheck.isValid()) {
            System.out.println(player + "wins the game");
            return;
          }
        }
        Map<SocketChannel, List<Instruction>> instrMap = recvInstrFromClients();
        executeAll(instrMap);
      } catch (IOException e) {
        System.out.println(e);
      }
      autoIncrement();
      cnt++;
    }
  }

  public void sendPlayerNames() throws IOException {
    Iterator<String> namesIter = board.getAllOwners().iterator();
    for (SocketChannel sc : playerSockets) {
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(namesIter.next());
    }
  }
  
  public void sendBoardToClients() throws IOException {
    for (SocketChannel sc : playerSockets) {
      sc.configureBlocking(true);
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(this.board);
    }
  }

  public Map<SocketChannel, List<Instruction>> recvInstrFromClients() throws IOException {
    InstructionCollector ic = new InstructionCollector(playerSockets);
    return ic.collect();
  }

  public void executeAll(Map<SocketChannel, List<Instruction>> instrMap) {
    // first move
    for (SocketChannel playerSocket : instrMap.keySet()) {
      for (Instruction instr : instrMap.get(playerSocket)) {
        if (instr instanceof Move) {
          instr.execute(board);
        }
      }
    }
    // then attack
    for (SocketChannel playerSocket : instrMap.keySet()) {
      for (Instruction instr : instrMap.get(playerSocket)) {
        if (instr instanceof Attack) {
          instr.execute(board);
        }
      }
    }
    // then resolve
    board.resolve();
  }

  public void autoIncrement() {
    for (Region r : board.getAllRegions()) {
      r.autoIncrement();
    }
    //for player:
    //  autoIncrement() resource
  }
}
