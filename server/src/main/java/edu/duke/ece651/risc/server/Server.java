package edu.duke.ece651.risc.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Server {
  private ServerSocketChannel serverSocketChannel;
  private Map<Integer, List<GameMaster>> games;

  public Server(int port) throws IOException {
    this.serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
    this.games = new HashMap<Integer, List<GameMaster>>();
    for (int i = 2; i < 6; i++) {
      games.put(i, new ArrayList<GameMaster>());
      games.get(i).add(new GameMaster(i));
    }
  }
 
  public static void main(String[] args) {
    try {
      Scanner config = new Scanner(new File("/src/resources/config.txt"));
      Server server = new Server(config.nextInt());
      AuthServer auth = new AuthServer(config.nextInt());
      Thread authServer = new Thread(auth);
      authServer.start();
      while (true) {
        SocketChannel sc = server.accept();
        int playerNum = server.getPlayerNum(sc);
        System.out.println("Requesting a game for " + playerNum);
        GameMaster gm = server.getGameFor(playerNum);
        gm.addPlayer(sc);
        if (gm.isFull()) {
          Thread gameMaster = new Thread(gm);
          gameMaster.start();
        }
      }
    } catch (IOException e) {
      System.out.println(e);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }

  public SocketChannel accept() throws IOException {
    return serverSocketChannel.accept();
  }
  
  public int getPlayerNum(SocketChannel sc) throws IOException, ClassNotFoundException{
    Socket s = sc.socket();
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    Integer playerNum = (Integer) deserial.readObject();
    return playerNum;
  }

  public GameMaster getGameFor(int playerNum) {
    List<GameMaster> gameList = games.get(playerNum);
    GameMaster game = gameList.get(gameList.size() - 1);
    if (game.isFull()) {
      game = new GameMaster(playerNum);
      games.get(playerNum).add(game);
    }
    return game;
  }
}
