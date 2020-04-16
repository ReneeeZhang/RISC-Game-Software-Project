package edu.duke.ece651.risc.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Server {
  private ServerSocketChannel serverSocketChannel;
  private Map<Integer, GameMaster> games;
  
  public Server(int port) throws IOException {
    this.serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
    this.games = new HashMap<Integer, GameMaster>();
    for (int i = 2; i < 6; i++) {
      games.put(i, new GameMaster(i));
    }
  }

  public static void main(String[] args) {
    try {
      Scanner config = new Scanner(new File("src/main/resources/config.txt"));
      Server server = new Server(config.nextInt());
      while (true) {
        server.handleRequest();
      }
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }

  public int getPlayerNum(SocketChannel sc) throws IOException, ClassNotFoundException{
    Socket s = sc.socket();
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    Integer playerNum = (Integer) deserial.readObject();
    return playerNum;
  }

  public GameMaster getGameFor(int playerNum) {
    GameMaster game = games.get(playerNum);
    if (game.isFull()) {
      game = new GameMaster(playerNum);
      games.put(playerNum, game);
    }
    return game;
  }

  public void handleRequest() throws IOException, ClassNotFoundException{
    SocketChannel sc = serverSocketChannel.accept();
    int playerNum = getPlayerNum(sc);
    GameMaster gm = getGameFor(playerNum);
    gm.addPlayer(sc);
    if (gm.isFull()) {
      Thread gameMaster = new Thread(gm);
      gameMaster.start();
    }
  }
}
