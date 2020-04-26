package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.client.controller.*;
import javafx.application.Application;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientGUITest {

  @Test
  void main() throws Exception {
    ClientGUI gui = new ClientGUI();
    GameController gControl = new GameController(gui);
    NumPlayersController nControl = new NumPlayersController(gui);
    LoginController lControl = new LoginController(gui);
    StartController sControl = new StartController(gui);
    WatchController wControl = new WatchController(gui);
    LoseController loControl = new LoseController(gui, 0);
    //ChatThread cThread = new ChatThread(gui, gControl, 0);
    //cThread.changeRoom(1);
  }
}
