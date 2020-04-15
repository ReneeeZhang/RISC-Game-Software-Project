package edu.duke.ece651.risc.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class AuthServer implements Runnable{
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

  public void run() {
    while (true) {
      Socket s = acceptSocket();
      while (s.isConnected()) {
        try{
          handleRequest(s);
        } catch (Exception e) {
          System.out.println(e);
        }
      }
    }
  }

  public Socket acceptSocket() {
    try{
      SocketChannel sc = serverSocketChannel.accept();
      return sc.socket();
    } catch (IOException e) {
      System.out.println(e);
      return new Socket();
    }
  }

  public void handleRequest(Socket s) throws IOException, ClassNotFoundException {
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    String login = (String) deserial.readObject();
    String user = login.substring(0, login.indexOf('&'));
    String password = login.substring(login.indexOf('&') + 2);
    ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
    if (password.equals(db.get(user))) {
      serial.writeObject("yes");
    } else {
      serial.writeObject("no");
    }
  }

  public void close() throws IOException {
    serverSocketChannel.close();
  }
}
