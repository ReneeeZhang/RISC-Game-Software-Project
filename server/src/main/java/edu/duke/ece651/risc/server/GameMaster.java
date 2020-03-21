package edu.duke.ece651.risc.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.BaseRegion;
import shared.BaseUnit;
import shared.Board;
import shared.GameBoard;
import shared.Instruction;
import shared.Region;
import shared.Unit;

public class GameMaster {
  private Board board;
  private List<SocketChannel> playerSockets;

  public GameMaster(List<SocketChannel> playerSockets) {
    this.playerSockets = playerSockets;
    this.board = initGameBoard();
  }

  private Board initGameBoard() {
    //TODO:config init boards for 2-5 players
    List<Unit> aUnits = new ArrayList<Unit>();
    aUnits.add(new BaseUnit("a"));
    List<Unit> bUnits = new ArrayList<Unit>();
    bUnits.add(new BaseUnit("b"));
    List<Unit> cUnits = new ArrayList<Unit>();
    cUnits.add(new BaseUnit("c"));
    Region a = new BaseRegion("Fitzpatrick", "PlayerA", "Red", aUnits);
    Region b = new BaseRegion("Hudson", "PlayerA", "Blue", bUnits);
    Region c = new BaseRegion("Teer", "PlayerA", "Green", cUnits);
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
      System.out.println(sc.isRegistered());
      System.out.println("send blocking: " + sc.isBlocking());
      sc.configureBlocking(true);
      Socket s = sc.socket();
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());
      ObjectOutputStream serial = new ObjectOutputStream(dout);
      serial.writeObject(this.board);
    }
  }

  public Map<SocketChannel, List<Instruction>> recvInstrFromClient() throws IOException {
    InstructionCollector ic = new InstructionCollector(playerSockets);
    return ic.collect();
  }

  public void resolve(Map<SocketChannel, List<Instruction>> instrMap) {
    for (SocketChannel playerSocket : instrMap.keySet()) {
      for (Instruction in : instrMap.get(playerSocket)) {
        in.execute(board);
      }
    }
  }
}
