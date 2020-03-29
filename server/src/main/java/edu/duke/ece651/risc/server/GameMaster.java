package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import shared.Attack;
import shared.Board;
import shared.Initializer;
import shared.Instruction;
import shared.Move;
import shared.Region;
import shared.checkers.Checker;
import shared.checkers.LoserChecker;
import shared.checkers.WinnerChecker;

public class GameMaster implements Runnable {
  private int playerNum;
  private Board board;
  private List<SocketChannel> playerSockets;
  private Map<SocketChannel, String> socketPlayerMap;

  public GameMaster(int n) {
    this.playerNum = n;
    try {
      Initializer initer = new Initializer(n);
      this.board = initer.initGame();
    } catch (IOException e) {
      System.out.println(e);
    }
    this.playerSockets = new ArrayList<SocketChannel>();
    this.socketPlayerMap = new HashMap<SocketChannel, String>();
  }

  public void run() {
    try{
      sendNameToClients();
    } catch (IOException e) {
      System.out.println(e);
    }
    int cnt = 1;
    while (true) {
      System.out.println("Round " + cnt + " Starts!");
      try{
        sendBoardToClients();
        for (SocketChannel sc :playerSockets) {
          String player = socketPlayerMap.get(sc);
          Checker winCheck = new WinnerChecker(board, player);
          Checker loseCheck = new LoserChecker(board, player);
          if (winCheck.isValid()) {
            System.out.println(player + "wins the game");
            return;
          }
          if (loseCheck.isValid()) {
            if (!recvYesFromClient(sc)) {
              playerSockets.remove(sc);
            }
          }
        }
        Map<SocketChannel, List<Instruction>> instrMap = recvInstrFromClients();
        executeAll(instrMap);
      } catch (IOException e) {
        System.out.println(e);
      }
      //autoIncrement();
      cnt++;
    }
  }

  public boolean isFull() {
    return playerNum == playerSockets.size();
  }

  public void addPlayer(SocketChannel sc) {
    playerSockets.add(sc);
  }
  
  public void sendNameToClients() throws IOException {
    Iterator<String> namesIter = board.getAllOwners().iterator();
    for (SocketChannel sc : playerSockets) {
      String name = namesIter.next();
      socketPlayerMap.put(sc, name);
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(name);
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

  public boolean recvYesFromClient(SocketChannel sc) throws IOException {
    sc.configureBlocking(true);
    Socket s = sc.socket();
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    try{
      String yes = (String) deserial.readObject();
      return yes.equals("yes");
    } catch (ClassNotFoundException e) {
      System.out.println(e);
      return false;
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
