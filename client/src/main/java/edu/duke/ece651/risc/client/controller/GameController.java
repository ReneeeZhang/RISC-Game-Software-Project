package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.*;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
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

  @FXML
  private BorderPane mainView;
  @FXML
  private TabPane games;
  @FXML
  private Tab newGame;
  @FXML
  private ChoiceBox<String> actionChoice;
  @FXML
  private VBox right;
  @FXML
  private VBox action;
  @FXML
  private Group group;
  @FXML
  private Circle color;
  @FXML
  private Label info;

  Board board;
  boolean init = true;
  int currentRoom;
  private static Map<String, Color> colorMapper = new HashMap<>();

  static {
    colorMapper.put("Player1", Color.BLUE);
    colorMapper.put("Player2", Color.YELLOW);
    colorMapper.put("Player3", Color.GREEN);
    colorMapper.put("Player4", Color.RED);
    colorMapper.put("Player5", Color.ORANGE);
  }
  public GameController addBoard(Board board) {
    this.board = board;
    return this;
  }

  public GameController addMap(Group group) {
    this.group = group;
    initMap();
    mainView.setCenter(group);
    return this;
  }

  public GameController setCurrentRoom(int room) {
    this.currentRoom = room;
    System.out.println("set current room " + currentRoom);
    games.getSelectionModel().select(currentRoom - 1);
    init = false;
    return this;
  }

  private void chooseAction(String a) {
    a = a.split(" ")[0];
    URL resource = getClass().getResource(String.format("/fxml/component/%s.fxml", a));
    try {
      action = FXMLLoader.load(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    right.getChildren().set(3, action);
    mainView.setRight(right);
    Stage window = (Stage)mainView.getScene().getWindow();
    window.setScene(mainView.getScene());
  }

  @FXML
  public void doDone() {
    System.out.println("Done");
  }

  private void initMap() {
    List<Region> allRegions = board.getAllRegions();
    for (Region region : allRegions) {
      String owner = region.getOwner().getName();
      Circle circle = (Circle)group.lookup("#" + region.getName());
      circle.setFill(colorMapper.get(owner));
    }
  }

  @FXML
  void createNewGame() throws IOException {
    System.out.println("start game " + (currentRoom + 1));
    gui.getClient().joinGame();
    gui.setNumPlayersScene();
  }

  private void generateTabs(int activeRoom) {
    int size = games.getTabs().size();
    while (size - 1 < activeRoom) {
      Tab tab = new Tab("Game " + size);
      tab.setId(String.valueOf(size));
      System.out.println("create tab with id " + size);
      tab.setOnSelectionChanged(event -> {
        if (init) return;
        if (tab.isSelected()) {
          String id = tab.getId();
          System.out.println("switch to room " + id);
          gui.setGameScene(Integer.parseInt(id));
        }
      });
      games.getTabs().add(size - 1, tab);
      size++;
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("initialize");
    generateTabs(gui.getActiveGames());
    actionChoice.getItems().addAll("move", "attack", "unit upgrade", "tech upgrade");
    actionChoice.setValue("move");
    actionChoice.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
      chooseAction(actionChoice.getItems().get((int)newValue));
    });
    URL resource = getClass().getResource("/fxml/component/move.fxml");
    try {
      action = FXMLLoader.load(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    right.getChildren().set(3, action);

  }
}
