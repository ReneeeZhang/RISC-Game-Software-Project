package edu.duke.ece651.risc.chatServer;

import java.io.ObjectInputStream;

public class FakeRecv implements Runnable {
  private ObjectInputStream ois;

  public FakeRecv(ObjectInputStream ois) {
    this.ois = ois;
  }

  public void run() {
    while (true) {
      try {
        String mesg = (String) ois.readObject();
      } catch (Exception e) {
      }  
    }
  }
}
