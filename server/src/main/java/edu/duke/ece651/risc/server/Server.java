package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
  private ServerSocketChannel serverSocketChannel;

  public Server(int port) throws IOException {
    this.serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
  }
  
  public static void main(String[] args) {
    try {
      Server server = new Server(Integer.valueOf(args[0]));
      System.out.println("Create a new game for how many players:");
      Scanner sc = new Scanner(System.in);
      int playerNum = sc.nextInt();
      sc.close();
      List<SocketChannel> clientSockets = server.waitForClients(playerNum);
      GameMaster gm = new GameMaster(clientSockets);
      gm.run();
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public List<SocketChannel> waitForClients(int n) throws IOException {
    List<SocketChannel> clientSockets = new ArrayList<SocketChannel>();
    for (int i = 0; i < n; i++) {
      SocketChannel sc = serverSocketChannel.accept();
      clientSockets.add(sc);
    }
    return clientSockets;
  }
}
