package edu.duke.ece651.risc.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

public class ChatController extends Connector {
  private ObjectOutputStream serial;
  private ObjectInputStream deserial;
  private final int SLEEP_TIME = 10;
  
  public ChatController(String hostname, int chatPort) {
    super(hostname, chatPort);
    try {
      this.serial = new ObjectOutputStream(socket.getOutputStream());
      System.out.println("chat controller OOS created");
      this.deserial = new ObjectInputStream(socket.getInputStream());
      System.out.println("chat controller OIS created");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    System.out.println("chatPort: " + chatPort);
  }

  @Override
  protected void send(Object obj) {
    while (true) {
      try {
        this.serial.writeObject(obj);
        System.out.println("No exception while sending");
        return;
      } catch (IOException ex) {
        System.out.println("I/O exception. Waiting for resending");
        try {
          TimeUnit.SECONDS.sleep(SLEEP_TIME);
        } catch (InterruptedException interruptex) {
          System.out.println("Interrupt exception.");
          continue;
        }
      }
    }
  }

  @Override
  protected Object receive() {
    while (true) {
      try {
        return this.deserial.readObject();
      } catch (IOException ioex) {
        System.out.println("I/O exception when receiving.");
        return null;
      } catch (ClassNotFoundException cfex) {
        System.out.println("ClassNotFound exception, check your protocol please.");
        return null;
      }
    }
  }
}
