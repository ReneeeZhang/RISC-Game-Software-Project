package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shared.Board;
import shared.Region;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class GameController {

  private ClientGUI gui;
  
  public GameController(ClientGUI g) {
    this.gui = g;
  }

  // @FXML
  // public void addAction() {

  // }

  // @FXML
  // public void commitAction() {

  // }

  @FXML
  private BorderPane mainView;
  @FXML
  private TabPane games;
  @FXML
  private Group group;
  @FXML
  private Circle color;
  @FXML
  private Label info;

  private static Map<String, Color> colorMapper = new HashMap<>();

  static {
    colorMapper.put("blue", Color.BLUE);
    colorMapper.put("yellow", Color.YELLOW);
    colorMapper.put("green", Color.GREEN);
    colorMapper.put("red", Color.RED);
    colorMapper.put("orange", Color.ORANGE);
  }
  public void setMap(Board board) {
    List<Region> allRegions = board.getAllRegions();
    for (Region region : allRegions) {
      String owner = region.getOwner();
      String color = region.getColor();
      Circle circle = (Circle)group.lookup("#" + owner);
      circle.setFill(colorMapper.get(color));
    }
  }
  /**
   * Add
   */
  public GameController addMap(Group group) {
    this.group = group;
    mainView.setCenter(group);
    return this;
  }
  @FXML
  public void createMove() {
    System.out.println("Move");
  }
  @FXML
  public void createAttack() {
    System.out.println("Attack");
  }
  @FXML
  public void createUpgrade() {
    System.out.println("Upgrade");
  }
  @FXML
  public void doDone() {
    System.out.println("Done");
  }

}
