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

import shared.Board;
import shared.Initializer;
import shared.Player;
import shared.Region;
import shared.checkers.Checker;
import shared.checkers.LoserChecker;
import shared.checkers.WinnerChecker;
import shared.instructions.Instruction;

public class GameMaster implements Runnable {
  private int playerNum;
  private Board board;
  private List<SocketChannel> playerSockets;
  private Map<SocketChannel, String> socketPlayerMap;
  private Set<String> loser;

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
    this.loser = new HashSet<String>();
  }

  public void run() {
    try {
      sendNameToClients();
    } catch (IOException e) {
      System.out.println("GameMaster raised an exception: " + e);
    }
    while (true) {
      try{
        sendBoardToClients();
        for (SocketChannel sc :playerSockets) {
          String player = socketPlayerMap.get(sc);
          Checker winCheck = new WinnerChecker(board, player);
          Checker loseCheck = new LoserChecker(board, player);
          // if somebody wins or no players left in the room
          if (winCheck.isValid() || playerSockets.size() == 0) {
            System.out.println(player + "wins the game");
            return;
          }
          if (loseCheck.isValid() && !loser.contains(player)) {
            System.out.println(player + "loses the game");
            if (recvYesFromClient(sc)) {
              loser.add(player);
              Socket s = sc.socket();
              ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
              serial.writeObject(this.board);
            } else {
              playerSockets.remove(sc);
            }
          }
        }
        Map<SocketChannel, List<Instruction>> instrMap = recvInstrFromClients();
        executeAll(instrMap);
        autoIncrement();
      } catch (IOException e) {
        System.out.println("GameMaster raised an exception");
        return;
      }
    }
  }

  public boolean isFull() {
    return playerNum == playerSockets.size();
  }

  public void addPlayer(SocketChannel sc) {
    if (sc.isConnected()) {
      playerSockets.add(sc);
    }
  }

  public void sendNameToClients() throws IOException {
    Iterator<String> namesIter = board.getAllOwners().iterator();
    for (SocketChannel sc : playerSockets) {
      if (sc.isConnected()) {
        String name = namesIter.next();
        socketPlayerMap.put(sc, name);
        Socket s = sc.socket();
        ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
        serial.writeObject(name);
      } else {
        playerSockets.remove(sc);
      }
    }
  }

  public void sendBoardToClients() throws IOException {
    for (SocketChannel sc : playerSockets) {
      if(sc.isConnected()) {
        //sc.configureBlocking(true);
        Socket s = sc.socket();
        ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
        serial.writeObject(this.board);
      } else {
        playerSockets.remove(sc);
      }
    }
  }

  public boolean recvYesFromClient(SocketChannel sc) throws IOException {
    //sc.configureBlocking(true);
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
    for (SocketChannel playerSocket : instrMap.keySet()) {
      for (Instruction instr : instrMap.get(playerSocket)) {
        instr.execute(board);
      }
    }
    board.resolve();
    // TODO: figure out when to execute ally instructions
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
