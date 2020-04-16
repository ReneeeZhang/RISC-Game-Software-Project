package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.*;
import shared.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NumPlayersController {

  @FXML
  private ChoiceBox<Integer> numChoice;
  private ClientGUI gui;
  
  public NumPlayersController(ClientGUI g) {
    this.gui = g;
    // this.numChoice = new ChoiceBox<>();
    // this.numChoice.getItems().addAll(2, 3, 4, 5);
  }

  @FXML
  public void selectNum() {
    try {
        gui.sendObj(gui.getActiveGames(), numChoice.getValue());
        System.out.println("send :" + numChoice.getValue());
      } catch (Exception ex1) {
        ex1.printStackTrace();
      }

      
        // Get player name and board
        String pName = new String();
        Board board = new GameBoard();
        
        // add name to list
        pName = gui.receiveStr(gui.getActiveGames());
        System.out.println("receive player name: " + pName);
        gui.addPlayerName(pName);
        gui.addPlayerNumber(numChoice.getValue());
        // init game
        board = (GameBoard) gui.receiveObj(gui.getActiveGames());
        System.out.println("receive board: ");

        gui.getClient().initMatch(gui.getActiveGames(), gui.getPlayerNames().get(gui.getActiveGames()), board);
        System.out.println("game inited");
        // increment active game count
        gui.setGameScene(gui.getActiveGames());
        gui.addActiveGame();
        System.out.println("Avtive games = " + gui.getActiveGames());  
  }

}
