package edu.duke.ece651.risc.authServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthServer{
  private ServerSocketChannel serverSocketChannel;
  private Map<String, String> db;

  public AuthServer(int port) throws IOException {
    this.serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
    this.db = new HashMap<String, String>();
    this.db.put("user", "passw0rd");
    for (int i = 0; i < 10; i++) {
      this.db.put("player" + Integer.toString(i), "duke" + Integer.toString(i));
    }
  }

  public static void main(String args[]) {
    try {
      AuthServer server = AuthServer.start("src/main/resources/config.txt");
      while (true) {
        server.handleRequest();
      }
    } catch (Exception e) {
    }
  }

  public static AuthServer start(String path) throws IOException{
    Scanner config = new Scanner(new File(path));
    return new AuthServer(config.nextInt());
  }
  
  public void handleRequest() throws IOException {
    SocketChannel sc = serverSocketChannel.accept();
    Thread t = new Thread(new Handler(sc.socket(), db));
    t.start();
  }
}
