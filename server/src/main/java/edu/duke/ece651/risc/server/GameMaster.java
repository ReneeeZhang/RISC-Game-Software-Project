package edu.duke.ece651.risc.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
  private List<Socket> players;

  public GameMaster(List<Socket> players) {
    this.players = players;
    int playerNum = players.size();
    this.board = initGameBoard(playerNum);
  }

  private Board initGameBoard(int playerNum) {
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
  
  public void sendToClient() throws IOException {
    for (Socket s : players) {
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());
      ObjectOutputStream serial = new ObjectOutputStream(dout);
      serial.writeObject(this.board);
    }
  }

  public void recvFromClient() throws IOException {
    for (Socket s : players) {
      DataInputStream din = new DataInputStream(s.getInputStream());
      ObjectInputStream deserial = new ObjectInputStream(din);
      try{
        Instruction instr = (Instruction) deserial.readObject();
        System.out.println(instr);
        instr.execute(this.board);
      } catch (ClassNotFoundException e) {
      System.out.println(e);
      }
    }
  }
}
