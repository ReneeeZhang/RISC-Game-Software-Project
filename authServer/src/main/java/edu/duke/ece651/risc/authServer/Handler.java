package edu.duke.ece651.risc.authServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class Handler implements Runnable {
  private Socket s;
  private Map<String, String> db;

  public Handler(Socket s, Map<String, String> db) {
    this.s = s;
    this.db = db;
  }

  public void run() {
    while (true) {
      try {
        handleRequest();
        if (s.isClosed()) {
          return;
        }
      } catch (Exception e) {
        return;
      }
    }
  }

  public void handleRequest() throws IOException, ClassNotFoundException {
    ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
    String login = (String) deserial.readObject();
    String user = login.substring(0, login.indexOf('&'));
    String password = login.substring(login.indexOf('&') + 2);
    ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
    if (password.equals(db.get(user))) {
      serial.writeObject("yes");
      s.close();
      return;
    } else {
      serial.writeObject("no");
    }
  }
}
