package edu.duke.ece651.risc;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {
  private Socket s;

  public Client() throws IOException{
    this.s = new Socket("localhost", 6666);
  }

  public void receive() throws IOException{
    DataInputStream din = new DataInputStream(s.getInputStream());
    ObjectMapper mapper = new ObjectMapper();
    String str = din.readUTF();
    Board b = mapper.readValue(str, Board.class);
    System.out.println(b);
  }
}
