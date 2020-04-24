package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.client.controller.GameController;

public class ChatThread implements Runnable {
  
  private ClientGUI gui;
  private GameController controller;
  private int room;

  public ChatThread(ClientGUI gui, GameController controller, int room) {
    this.gui = gui;
    this.controller = controller;
    this.room = room;
  }

  @Override
  public void run() {
    while(true) {
      String str = gui.getClient().receiveChatMsg(room);
      System.out.println("Message received ====================");
      controller.appendMsg(str);
    }
  }

  public void changeRoom(int newRoom) {
    this.room = newRoom;
  }
}
