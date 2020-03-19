package edu.duke.ece651.risc.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import shared.*;

public class Server {
  private Board board;
  private ServerSocket ss;
  private List<Socket> clients;
  
  public Server(int port, int playerNum, Board board) throws IOException {
    this.board = board;
    this.ss = new ServerSocket(port);
    this.clients = new ArrayList<Socket>();
  }
  
  public static void main(String[] args) {
    try {
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
      List<Region> cNeigh = new ArrayList<Region>();
      cNeigh.add(a);
      cNeigh.add(b);
      Map<Region, List<Region>> regionMap = new HashMap<Region, List<Region>>();
      regionMap.put(a, aNeigh);
      regionMap.put(b, bNeigh);
      regionMap.put(c, cNeigh);
      Board board = new GameBoard(regionMap);
      Server server = new Server(Integer.valueOf(args[0]), Integer.valueOf(args[1]), board);
      server.waitForClients(1);
      System.out.println("wait ends");
      server.sendToClient();
      server.recvFromClient();
      server.sendToClient();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void Initialization() {
  }
  
  public void waitForClients(int playerNum) throws IOException{
    for (int i = 0; i < playerNum; i++) {
      Socket s = ss.accept();
      System.out.println("accept");
      clients.add(s);
    }
    ss.close();
  }

  public void sendToClient() throws IOException {
    for (Socket s : clients) {
      DataOutputStream dout = new DataOutputStream(s.getOutputStream());
      ObjectOutputStream serial = new ObjectOutputStream(dout);
      serial.writeObject(this.board);
    }
  }

  public void recvFromClient() throws IOException {
    for (Socket s : clients) {
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
