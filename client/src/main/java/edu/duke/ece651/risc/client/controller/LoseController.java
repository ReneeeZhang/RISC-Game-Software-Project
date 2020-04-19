package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoseController {

  private ClientGUI gui;
  private int currentRoom;
  
  public LoseController(ClientGUI g, int room) {
    this.gui = g;
    currentRoom = room;
  }

  @FXML
  public void watchGame() {
    try {
        gui.setWatchScene(currentRoom);
        gui.sendStr(currentRoom, "yes");
    } catch(Exception ex) {
        ex.printStackTrace();
    }
     
  }

}
