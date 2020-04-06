package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client extends Connector {
  private String hostname;
  private int ccPort;
  private List<GameJoiner> matches;

  
  public Client(String hostname, int authenticationPort, int ccPort) throws IOException {
    super(hostname, authenticationPort);
    this.hostname = hostname;
    this.ccPort = ccPort;
    matches = new ArrayList<>();
  }

  public void joinGame() throws IOException {
    GameJoiner gj = new GameJoiner(hostname, ccPort);
    matches.add(gj);
  }
}
