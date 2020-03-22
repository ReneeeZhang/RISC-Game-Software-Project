package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import shared.Instruction;

public class Server {
  private ServerSocketChannel serverSocketChannel;

  public Server(int port, Scanner sc) throws IOException {
    this.serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
  }
  
  public static void main(String[] args) {
    try {
      Server server = new Server(Integer.valueOf(args[0]), new Scanner(System.in));
      System.out.println("Create a new game for how many players:");
      Scanner sc = new Scanner("2");
      int playerNum = Integer.valueOf(sc.nextLine());
      sc.close();
      List<SocketChannel> clientSockets = server.waitForClients(playerNum);
      GameMaster gm = new GameMaster(clientSockets);
      // actual game starts
      while (true) {
        System.out.println("Round Start");
        gm.sendBoardToClient();
        Map<SocketChannel, List<Instruction>> instrMap = gm.recvInstrFromClient();
        gm.resolve(instrMap);
        // if win() is true, break
      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public List<SocketChannel> waitForClients(int n) throws IOException {
    List<SocketChannel> clientSockets = new ArrayList<SocketChannel>();
    //System.out.println("wait");
    for (int i = 0; i < n; i++) {
      SocketChannel sc = serverSocketChannel.accept();
      clientSockets.add(sc);
    }
    return clientSockets;
  }
}
