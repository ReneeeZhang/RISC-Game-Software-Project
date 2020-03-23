package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.*;

public class GameMaster {
  private Board board;
  private List<SocketChannel> playerSockets;

  public GameMaster(List<SocketChannel> playerSockets) {
    this.playerSockets = playerSockets;
    this.board = initGameBoard();
  }

  public void run() throws IOException{
    while (true) {
      System.out.println("Round Start");
      sendBoardToClient();
      Map<SocketChannel, List<Instruction>> instrMap = recvInstrFromClient();
      resolve(instrMap);
      //TODO:if win() is true, break
    }
  }

  private Board initGameBoard() {
    //TODO:config init boards for 2-5 players
    List<Unit> aUnits = new ArrayList<Unit>();
    aUnits.add(new BaseUnit(""));
    List<Unit> bUnits = new ArrayList<Unit>();
    bUnits.add(new BaseUnit(""));
    List<Unit> cUnits = new ArrayList<Unit>();
    cUnits.add(new BaseUnit(""));
    Map<String, List<Unit>> aBorderCamps = new HashMap<String, List<Unit>>();
    aBorderCamps.put("Hudson", new ArrayList<Unit>());
    Map<String, List<Unit>> bBorderCamps = new HashMap<String, List<Unit>>();
    bBorderCamps.put("Fitzpatrick", new ArrayList<Unit>());
    bBorderCamps.put("Teer", new ArrayList<Unit>());
    Map<String, List<Unit>> cBorderCamps = new HashMap<String, List<Unit>>();
    cBorderCamps.put("Hudson", new ArrayList<Unit>());
    Region a = new BaseRegion("Fitzpatrick", "PlayerA", "Red", aUnits, aBorderCamps);
    Region b = new BaseRegion("Hudson", "PlayerA", "Blue", bUnits, bBorderCamps);
    Region c = new BaseRegion("Teer", "PlayerA", "Green", cUnits, cBorderCamps);
    List<Region> aNeigh = new ArrayList<Region>();
    aNeigh.add(b);
    List<Region> bNeigh = new ArrayList<Region>();
    bNeigh.add(a);
    bNeigh.add(c);
    List<Region> cNeigh = new ArrayList<Region>();
    cNeigh.add(b);
    Map<Region, List<Region>> regionMap = new HashMap<Region, List<Region>>();
    regionMap.put(a, aNeigh);
    regionMap.put(b, bNeigh);
    regionMap.put(c, cNeigh);
    return new GameBoard(regionMap);
  }

  public void sendBoardToClient() throws IOException {
    for (SocketChannel sc : playerSockets) {
      sc.configureBlocking(true);
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(this.board);
    }
  }

  public Map<SocketChannel, List<Instruction>> recvInstrFromClient() throws IOException {
    InstructionCollector ic = new InstructionCollector(playerSockets);
    return ic.collect();
  }

  public void resolve(Map<SocketChannel, List<Instruction>> instrMap) {
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
    //for()
  }
}
