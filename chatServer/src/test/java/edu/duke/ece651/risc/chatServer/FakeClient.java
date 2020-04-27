package edu.duke.ece651.risc.chatServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
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
      ObjectInputStream deserial = new ObjectInputStream(sc.socket().getInputStream());
      ObjectOutputStream serial = new ObjectOutputStream(sc.socket().getOutputStream());

      Thread recv = new Thread(new FakeRecv(deserial));
      recv.start();

      // send player num
      serial.writeObject(2);

      // if has input, send mesg
      if (input.hasNext()) {
        serial.writeObject(input.next());
      }

      sc.close();
    } catch (Exception e) {
    }
  }
}
