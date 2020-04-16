package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

public class GameController implements Initializable{

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
  private ChoiceBox<String> actionChoice;
  @FXML
  private VBox action;
  private VBox actionPane;
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
      String owner = region.getOwner().getName();
      Circle circle = (Circle)group.lookup("#" + owner);
      circle.setFill(colorMapper.get(color));
    }
  }

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


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    actionChoice.getItems().addAll("MOVE", "ATTACK", "UPGRADE");

    URL resource = getClass().getResource("/fxml/component/move.fxml");
    try {
      actionPane = FXMLLoader.load(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
