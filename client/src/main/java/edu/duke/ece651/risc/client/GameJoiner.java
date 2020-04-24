package edu.duke.ece651.risc.client;

import shared.Board;

import shared.instructions.*;

import shared.checkers.Checker;
import shared.checkers.GameOverChecker;
import shared.checkers.LoserChecker;
import shared.checkers.WinnerChecker;

public class GameJoiner extends Connector {
  private String name;
  private Board board;
  private ChatController chatController;

  public GameJoiner(String hostname, int gamePort, int chatPort) {
    super(hostname, gamePort);
    System.out.println("Game server connected");
    chatController = new ChatController(hostname, chatPort);
    System.out.println("Chat Server connected");
  }

  // Must init GameJoiner after receiving name and board
  public void init(String name, Board board) {
    setName(name);
    setBoard(board);
  }

  public void sendChatMsg(String msg) {
    System.out.println("msg coming to gamejoinger: " + msg);
    chatController.send(name + ": " + msg);
    System.out.println("msg successfully sent");
  }

  public void sendNumPlayerToChat(int numPlayer) {
    chatController.send(numPlayer);
  }
  
  public String receiveChatMsg() {
    String msg = (String) chatController.receive();
    String[] splittedMsg = msg.split(": ");
    if (splittedMsg[0].equals(this.name)) {
      return "You: " + splittedMsg[1];
    }
    return msg;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Board getBoard() {
    return board;
  }
  
  public void setBoard(Board board) {
    this.board = board;
  }

  public boolean isValidInst(Instruction inst) {
    if (inst.isValid(board)) {
      inst.execute(board);
      return true;
    }
    return false;
  }
  
  public boolean hasWon() {
    Checker winnerChecker = new WinnerChecker(board, name);
    if (winnerChecker.isValid()) {
      System.out.println(name + ", you have won!");
      return true;
    }
    return false;
  }

  public boolean hasLost() {
    Checker loserChecker = new LoserChecker(board, name);
    if (loserChecker.isValid()) {
      System.out.println(name + ", you have lost...");
      return true;
    }
    return false;
  }

  public boolean isGameOver(){
    GameOverChecker gmoChecker = new GameOverChecker(board);
    return gmoChecker.isValid();
  }
  /**
   * Check game result based on given {@argv board}
   * Return true if this client is the winner
   * Return false if this client loses  
   */
  /*
  private void checkResult(GameBoard board) {
    if (hasWon(board)) {
      System.out.println("Game over~");
      return true;
    }    
  }
  
   public void run() {
    try {
      //send(3); // want to join a game of 2. Send the number of players to server
      this.name = (String) receive();
      while (true) {
        // receive the board from GameMaster
        GameBoard board = (GameBoard)receive();
        if (hasWon()) {
          System.out.println("Game over~");
          return;
        }
        if (hasLost()) {
          System.out.println("Would you like to continue to watch the game? Please answer yes/no:");
          while (true) {
            String ans2Lost = scanner.nextLine().toLowerCase();

            if (ans2Lost.equals("yes")) {
              send(ans2Lost); // Send "yes" to server
              while (true) {
                GameBoard board2Watch = (GameBoard) receive();
                System.out.println(board2Watch.draw());
                GameOverChecker gmoChecker = new GameOverChecker(board2Watch);
                if (gmoChecker.isValid()) {
                  System.out.println("Game over~");
                  return;
                }
                else {
                  send(new ArrayList<Instruction>());
                }
              }
            }
            else if (ans2Lost.equals("no")) {
              send(ans2Lost);  // send "no" to server
              return;
            }
            else {
              System.out.println("You can only input yes or no.");
            }              
          }
        }
        System.out.println(board.draw());
        // integrate all the instructions that a user input
        List<Instruction> collectedInsts = collectInsts(board);
        // send those packed instructions to server
        send(collectedInsts);
      }
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }
  */
}
