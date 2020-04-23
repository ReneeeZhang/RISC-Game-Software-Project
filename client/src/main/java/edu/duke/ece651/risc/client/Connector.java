package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public abstract class Connector {
  protected Socket socket;
  private final int SLEEP_TIME = 10;
  
  public Connector(String hostname, int port) {
    makeSureConnection(hostname, port);
  }

  private void makeSureConnection(String hostname, int port) {
    while (true) {
      try{
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress(hostname, port));
        this.socket = sc.socket();
        return;
      } catch (IOException ex) {
        System.out.println("Game server in " + hostname + " has not prepared yet. Wait for reconnection.");
        try{
          TimeUnit.SECONDS.sleep(SLEEP_TIME);
        } catch (InterruptedException interruptEx) {
          continue;
        }
      }
    }
  }

  protected Object receive() {
    while (true) {
      try {
        ObjectInputStream deserial = new ObjectInputStream(socket.getInputStream());
        return deserial.readObject();
      } catch (IOException ioex) {
        System.out.println("I/O exception when receiving.");
        return null;
      } catch (ClassNotFoundException cfex) {
        System.out.println("ClassNotFound exception, check your protocol please.");
        return null;
      }
    }
  }

  protected void send(Object obj) {
    while (true) {
      try {
        ObjectOutputStream serial = new ObjectOutputStream(socket.getOutputStream());
        serial.writeObject(obj);
        return;
      } catch (IOException ex) {
        System.out.println("I/O exception. Waiting for resending");
        try {
          TimeUnit.SECONDS.sleep(SLEEP_TIME);
        } catch (InterruptedException interruptex) {
          continue;
        }
      }
    }
  }
}
