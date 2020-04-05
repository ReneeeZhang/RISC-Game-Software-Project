package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client extends Connector {
  private String hostname;
  private int port;
  private List<GameJoiner> matches;

  
  public Client(String hostname, int port) throws IOException {
    super(hostname, port);
    this.hostname = hostname;
    this.port = port;
    matches = new ArrayList<>();
  }

  public void joinGame() throws IOException {
    GameJoiner gj = new GameJoiner(hostname, port);
    matches.add(gj);
    Thread t = new Thread(gj);
    t.start();
  }
}
