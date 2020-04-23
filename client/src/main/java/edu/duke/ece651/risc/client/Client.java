package edu.duke.ece651.risc.client;

import java.util.ArrayList;
import java.util.List;
import shared.Board;
import shared.instructions.*;

public class Client extends Connector {
  private String hostname;
  private int gamePort;
  private int chatPort;
  private List<GameJoiner> matches;

  public Client(String hostname, int authenticationPort, int gamePort, int chatPort) {
    super(hostname, authenticationPort);
    this.hostname = hostname;
    this.gamePort = gamePort;
    this.chatPort = chatPort;
    this.matches = new ArrayList<>();
  }

  public void joinGame() {
    GameJoiner gj = new GameJoiner(hostname, gamePort, chatPort);
    matches.add(gj);
  }

  public void sendViaChannel(int matchIdx, Object obj) {
    matches.get(matchIdx).send(obj);
  }

  public Object receiveViaChannel(int matchIdx) {
    return matches.get(matchIdx).receive();
  }

  /*
    public Board receiveBoardViaChannel(int matchIdx) throws IOException, ClassNotFoundException {
    Board board = (Board) matches.get(matchIdx).receive();
    setBoard(matchIdx, board);
    return board;
  }
  */
  
  public void initMatch(int matchIdx, String name, Board board) {
    matches.get(matchIdx).init(name, board);
  }

  public void sendNumPlayer(int matchIdx, int numPlayer) {
    matches.get(matchIdx).send(numPlayer);
    matches.get(matchIdx).sendNumPlayerToChat(numPlayer);
  }

  public void sendChatMsg(int matchIdx, String msg) {
    matches.get(matchIdx).sendChatMsg(msg);
  }
  
  public String receiveChatMsg(int matchIdx) {
    return matches.get(matchIdx).receiveChatMsg();
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
