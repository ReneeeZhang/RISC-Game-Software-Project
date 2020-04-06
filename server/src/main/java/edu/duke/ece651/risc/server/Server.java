package edu.duke.ece651.risc.server;

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
      // TODO: config port num
      
      AuthServer auth = new AuthServer(6666);
      Thread tAuth = new Thread(auth);
      tAuth.start();
      Server server = new Server(7777);
      while (true) {
        SocketChannel sc = server.accept();
        int playerNum = server.getPlayerNum(sc);
        System.out.println("Request a game for " + playerNum);
        GameMaster gm = server.getGameFor(playerNum);
        gm.addPlayer(sc);
        if (gm.isFull()) {
          Thread t = new Thread(gm);
          t.start();
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
