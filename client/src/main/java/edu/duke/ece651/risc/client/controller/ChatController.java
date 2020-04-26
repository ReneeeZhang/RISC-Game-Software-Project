package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.ChatThread;
import edu.duke.ece651.risc.client.ClientGUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {

    public ChatController(ClientGUI gui) {
        this.gui = gui;
    }
    @FXML
    private TextArea area;
    @FXML private TextField input;
    @FXML private Button send;

    private ClientGUI gui;
    int currentRoom;
    ChatThread chat;
    @FXML public void send() {
    System.out.println("Send() called ========");
    String message = input.getText();
    input.clear();
    System.out.println("Message collected ===============");
    // String currentName = gui.getCurrentName(currentRoom - 1);
    //send message
    System.out.println("CurrentRoom = " + currentRoom);
    gui.getClient().sendChatMsg(currentRoom - 1, message);
    System.out.println("Message sent ====================");
  }

  //append message
  public void appendMsg(String message) {
    area.appendText(message);
  }

  public void startChat() {
    this.chat = new ChatThread(gui, this, currentRoom);
    Thread thread = new Thread(chat);
    thread.start();
  }
  public void setCurrentRoom(int room) {
        this.currentRoom = room;
  }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        startChat();
    }
}
