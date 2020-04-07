package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import shared.Board;
import shared.instructions.*;

public class Client extends Connector {
  private String hostname;
  private int ccPort;
  private List<GameJoiner> matches;

  
  public Client(String hostname, int authenticationPort, int ccPort) throws IOException {
    super(hostname, authenticationPort);
    this.hostname = hostname;
    this.ccPort = ccPort;
    matches = new ArrayList<>();
  }

  public void joinGame() throws IOException {
    GameJoiner gj = new GameJoiner(hostname, ccPort);
    System.out.println("ccport =============" + ccPort);
    matches.add(gj);
  }

  public void sendViaChannel(int matchIdx, Object obj) throws IOException {
    matches.get(matchIdx).send(obj);
  }

  public Object receiveViaChannel(int matchIdx) throws IOException, ClassNotFoundException {
    return matches.get(matchIdx).receive();
  }

  public void initMatch(int matchIdx, String name, Board board) {
    matches.get(matchIdx).init(name, board);
  }

  public Board getBoard(int matchIdx) {
    return matches.get(matchIdx).getBoard();
  }
  
  public void setBoard(int matchIdx, Board board) {
    matches.get(matchIdx).setBoard(board);
  }
  
  public boolean hasWon(int matchIdx) {
    return matches.get(matchIdx).hasWon();
  }

  public boolean hasLost(int matchIdx) {
    return matches.get(matchIdx).hasLost();
  }

  public boolean isGameOver(int matchIdx) {
    return matches.get(matchIdx).isGameOver();
  }

  public boolean isValidInst(int matchIdx, Instruction inst) {
    return matches.get(matchIdx).isValidInst(inst);
  }
}
