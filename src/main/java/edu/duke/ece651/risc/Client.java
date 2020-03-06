package edu.duke.ece651.risc;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {
  private Socket s;

  public Client() throws IOException{
    this.s = new Socket("localhost", 6666);
  }

  public void receive() throws IOException{
    DataInputStream din = new DataInputStream(s.getInputStream());
    ObjectInputStream deserial = new ObjectInputStream(din);
    try{
      Board b = (GameBoard) deserial.readObject();
      System.out.println(b);
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
    /*
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    byte[] buf = new byte[1024];
    din.read(buf);
    Board b = mapper.readValue(buf, GameBoard.class);
    */
  }
}
