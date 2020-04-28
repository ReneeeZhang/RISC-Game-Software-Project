package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.client.controller.ChatController;

public class ChatThread implements Runnable {
  
  private ClientGUI gui;
  private ChatController controller;
  private int room;

  public ChatThread(ClientGUI gui, ChatController controller, int room) {
    this.gui = gui;
    this.controller = controller;
    this.room = room;
  }

  @Override
  public void run() {
    // while(true) {
    //   System.out.println("loop in chat thread =========");
    //   String str = gui.getClient().receiveChatMsg(room);
    //   System.out.println("Message received ====================");
    //   controller.appendMsg(str);
    // }
  }

  public void send(String message) {
    // gui.getClient().sendChatMsg(room, message);
  }
    

  public void changeRoom(int newRoom) {
    this.room = newRoom;
  }
}
