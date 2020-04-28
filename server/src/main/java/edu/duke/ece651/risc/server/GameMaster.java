package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shared.GameBoard;
import shared.Initializer;
import shared.Player;
import shared.Region;
import shared.checkers.Checker;
import shared.checkers.LoserChecker;
import shared.checkers.WinnerChecker;
import shared.instructions.InciteDefection;
import shared.instructions.Instruction;

public class GameMaster implements Runnable {
  private int playerNum;
  private GameBoard board;
  private List<SocketChannel> playerSockets;
  private Map<SocketChannel, String> socketPlayerMap;
  private Set<String> loser;

  public GameMaster(int n) {
    this.playerNum = n;
    try {
      Initializer initer = new Initializer(n);
      this.board = initer.initGame();
    } catch (IOException e) {
    }
    this.playerSockets = new ArrayList<SocketChannel>();
    this.socketPlayerMap = new HashMap<SocketChannel, String>();
    this.loser = new HashSet<String>();
  }

  public void run() {
    try {
      sendNameToClients();
    } catch (IOException e) {
    }
    while (true) {
      try {
        System.out.println(board);
        sendBoardToClient();
        for (SocketChannel sc : playerSockets) {
          String player = socketPlayerMap.get(sc);
          Checker winCheck = new WinnerChecker(board, player);
          Checker loseCheck = new LoserChecker(board, player);
          // if somebody wins or no players left in the room
          if (winCheck.isValid() || playerSockets.size() == 0) {
            return;
          }
          if (loseCheck.isValid() && !loser.contains(player)) {
            if (recvYesFromClient(sc)) {
              loser.add(player);
              sendBoardToClient(sc);
            } else {
              playerSockets.remove(sc);
            }
          }
        }
        Map<SocketChannel, List<Instruction>> instrMap = recvInstrFromClients();
        executeAll(instrMap);
        autoIncrement();
      } catch (IOException e) {
        return;
      }
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

  public void sendBoardToClient() throws IOException {
    for (SocketChannel sc : playerSockets) {
      sendBoardToClient(sc);
    }
  }

  public void sendBoardToClient(SocketChannel sc) throws IOException {
    Socket s = sc.socket();
    ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
    serial.writeObject(board);
  }

  public boolean recvYesFromClient(SocketChannel sc) throws IOException {
    Socket s = sc.socket();
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    try{
      String yes = (String) deserial.readObject();
      return yes.equals("yes");
    } catch (ClassNotFoundException e) {
      return false;
    }
  }
  
  public Map<SocketChannel, List<Instruction>> recvInstrFromClients() throws IOException {
    InstructionCollector ic = new InstructionCollector(playerSockets);
    return ic.collect();
  }

  public void executeAll(Map<SocketChannel, List<Instruction>> instrMap) {
    for (SocketChannel playerSocket : instrMap.keySet()) {
      for (Instruction instr : instrMap.get(playerSocket)) {
        // execute all but incite defection
        if(!(instr instanceof InciteDefection)){
          instr.execute(board);
        }
      }
    }
    for (SocketChannel playerSocket : instrMap.keySet()) {
      for (Instruction instr : instrMap.get(playerSocket)) {
        // execute incite defection at the end of one round
        if(instr instanceof InciteDefection){
          instr.execute(board);
        }
      }
    }
    board.resolve();
    board.resolveAlly();
  }

  public void autoIncrement() {
    for (Region r : board.getAllRegions()) {
      r.autoIncrement();
    }
    for (String player : board.getAllOwners()) {
      Player p = board.getPlayer(player);
      int resource = 0;
      for (Region r : board.getAllRegions(player)) {
        resource += r.getResourceProduction();
      }
      p.increaseFood(resource);
      p.increaseTech(resource);
    }
  }
}
