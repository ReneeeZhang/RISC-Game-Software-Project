package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import shared.*;

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
      int playerNum = Integer.valueOf(System.in.toString());
      List<SocketChannel> clientSockets = server.waitForClients(playerNum);
      GameMaster gm = new GameMaster(clientSockets);
      // actual game starts
      while (true) {
        gm.sendBoardToClient();
        Map<Socket, List<Instruction>> instrMap = gm.recvInstrFromClient();
        gm.resolve(instrMap);
        // if win() is true, break
      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }
  
  public List<SocketChannel> waitForClients(int n) throws IOException {
    List<SocketChannel> clientSockets = new ArrayList<SocketChannel>();
    for (int i = 0; i < n; i++) {
      SocketChannel sc = serverSocketChannel.accept();
      System.out.println("accept");
      clientSockets.add(sc);
    }
    return clientSockets;
  }
}
