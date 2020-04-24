package edu.duke.ece651.risc.chatServer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class FakeClient implements Runnable {
  private Scanner input;
  
  public FakeClient(String s) {
    this.input = new Scanner(s);
  }

  public void run() {
    try {
      Thread.sleep(50);
      SocketChannel sc = SocketChannel.open();
      sc.connect(new InetSocketAddress("localhost", 8888));
      Socket s = sc.socket();
      ObjectOutputStream serial = new ObjectOutputStream(s.getOutputStream());
      serial.writeObject(2);
      if (input.hasNext()) {
        serial = new ObjectOutputStream(s.getOutputStream());
        serial.writeObject(input.next());
      }
      ObjectInputStream deserial = new ObjectInputStream(s.getInputStream());
      String mesg = (String) deserial.readObject();
      sc.close();
    } catch (Exception e) {
    }
  }
}
