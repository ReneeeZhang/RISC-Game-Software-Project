package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.util.List;

public class Client extends Connector {
  private List<GameJoiner> matches;

  public Client(String hostname, int port) throws IOException {
    super(hostname, port);
  }

  public static void main(String[] args) {
    try{
      Client client = new Client(args[0], Integer.parseInt(args[1]));
      // TODO: for debug
      System.out.println("Client connected");
      while (true) {
        // TODO: authentication omitted
        
        // TODO: something must trigger multi-thread
      }
    }
    catch (IOException e) {
      System.out.println(e);
    }
  }
}
