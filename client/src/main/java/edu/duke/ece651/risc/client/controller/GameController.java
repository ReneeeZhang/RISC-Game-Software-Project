package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import shared.*;
import shared.instructions.*;
import shared.checkers.*;

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
import java.util.*;

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
  private ArrayList<Instruction> insList = new ArrayList<>();

  static {
    colorMapper.put("Player1", Color.BLUE);
    colorMapper.put("Player2", Color.YELLOW);
    colorMapper.put("Player3", Color.GREEN);
    colorMapper.put("Player4", Color.RED);
    colorMapper.put("Player5", Color.ORANGE);
  }
  public GameController addBoard(Board board) {
    this.board = board;
    String currentName = gui.getCurrentName(currentRoom - 1);
    this.color.setFill(colorMapper.get(currentName));
    String s = String.format("Name: %s\nLevel: %s\nFood resource: %s\nTech resource: %s\n",
            currentName,
            board.getPlayer(currentName).getCurrLevel(),
            board.getPlayer(currentName).getFoodAmount(),
            board.getPlayer(currentName).getTechAmount());
    this.info.setText(s);
    chooseAction("move");
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
    setSrcChoice(gui.getCurrentName(currentRoom-1));
    setDestChoice(gui.getCurrentName(currentRoom - 1));
  }

  private void refreshPage() {
    mainView.setRight(right);
    Stage window = (Stage)mainView.getScene().getWindow();
    window.setScene(mainView.getScene());
  }

  @FXML
  public void doAdd() {
    int room = currentRoom - 1;
    String pname = gui.getCurrentName(room);
    System.out.println("pname is doing add");
    // Move
    if (actionChoice.getValue().equals("move")) {
      VBox entry = (VBox) right.getChildren().get(3);
      ChoiceBox<String> src = (ChoiceBox<String>) entry.getChildren().get(1);
      ChoiceBox<String> dest = (ChoiceBox<String>) entry.getChildren().get(3);
      TextField level = (TextField) entry.getChildren().get(5);
      TextField num = (TextField) entry.getChildren().get(7);
      
      Move moveIns = new Move(pname, src.getValue(), dest.getValue(),
                                  Integer.parseInt(level.getText()), Integer.parseInt(num.getText()));
                                 
          if(gui.getClient().isValidInst(room, moveIns)) {
              insList.add(moveIns);
              Popup.showInfo("Instruction added!");
          }
          else {
            Popup.showInfo("Invalid instruction!");
          }
              

        }
      // Attack
        else if (actionChoice.getValue().equals("attack")) {
          VBox entry = (VBox) right.getChildren().get(3);
          ChoiceBox<String> src = (ChoiceBox<String>) entry.getChildren().get(1);
          ChoiceBox<String> dest = (ChoiceBox<String>) entry.getChildren().get(3);
          TextField level = (TextField) entry.getChildren().get(5);
          TextField num = (TextField) entry.getChildren().get(7);
      
          Attack attackIns = new Attack(pname, src.getValue(), dest.getValue(),
                                  Integer.parseInt(level.getText()), Integer.parseInt(num.getText()));
          if(gui.getClient().isValidInst(room, attackIns)) {
            insList.add(attackIns);
            Popup.showInfo("Instruction added!");
          }
          else {
            Popup.showInfo("Invalid instruction!");
          }
        }
      // Upgrade unit
        else if (actionChoice.getValue().equals("unit upgrade")) {
          VBox entry = (VBox) right.getChildren().get(3);
          ChoiceBox<String> region = (ChoiceBox<String>) entry.getChildren().get(1);
          TextField oldlevel = (TextField) entry.getChildren().get(3);
          TextField newlevel = (TextField) entry.getChildren().get(5);
          TextField num = (TextField) entry.getChildren().get(7);
          
          UnitUpgrade upUnitIns = new UnitUpgrade(pname, region.getValue(), Integer.parseInt(oldlevel.getText()),
                                                  Integer.parseInt(newlevel.getText()),Integer.parseInt(num.getText()));
          if(gui.getClient().isValidInst(room, upUnitIns)) {
            insList.add(upUnitIns);
            Popup.showInfo("Instruction added!");
          }
          else {
            Popup.showInfo("Invalid instruction!");
          }
        }
      // Upgrade technology
        else if (actionChoice.getValue().equals("tech upgrade")) {
      
          TechUpgrade upTechIns = new TechUpgrade(pname, board.getPlayer(pname).getCurrLevel(), board.getPlayer(pname).getCurrLevel()+1);
          if(gui.getClient().isValidInst(room, upTechIns)) {
            insList.add(upTechIns);
            Popup.showInfo("Instruction added!");
          }
          else {
            Popup.showInfo("Invalid instruction!");
          }
        }
  }

  @FXML
  public void doDone() {
    // send instructions, set board
    int room = currentRoom - 1;
    gui.sendObj(room, insList);

    
        Board newBoard = (GameBoard) gui.receiveObj(room);
        System.out.println(newBoard.toString());
        gui.getClient().setBoard(room, newBoard);
        Popup.showInfo("Instructrion submitted.");
        insList.clear();

        // check win/lose
        if (gui.getClient().hasWon(room)) {
          System.out.println("you win!");
          gui.setWinScene();
        }
        else if (gui.getClient().hasLost(room)) {
          System.out.println("you lost!");
          gui.setLoseScene(room);
        }
     gui.setGameScene(currentRoom);
  }

  private void initMap() {
    List<Region> allRegions = board.getAllRegions();
    for (Region region : allRegions) {
      String owner = region.getOwner().getName();
      Circle circle = (Circle)group.lookup("#" + region.getName());
      circle.setOnMouseClicked(this::showInfo);
      circle.setFill(colorMapper.get(owner));
    }
  }

  @FXML
  void createNewGame() throws IOException {
    System.out.println("start game " + (currentRoom + 1));
    gui.getClient().joinGame();
    gui.setNumPlayersScene();
  }

  @FXML
  public void showInfo(MouseEvent event) {
    Node source = (Node)event.getSource();
    String id = source.getId();
    Popup.showInfo(board.getRegion(id).getInfo());
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



  public void setSrcChoice(String pname) {
    VBox entry = (VBox) right.getChildren().get(3);
    ChoiceBox<String> srcChoice = (ChoiceBox<String>) entry.getChildren().get(1);
    srcChoice.getItems().addAll(board.getRegionNames(pname));
  }

  public void setDestChoice(String pname) {
    VBox entry = (VBox) right.getChildren().get(3);
    ChoiceBox<String> destChoice = (ChoiceBox<String>) entry.getChildren().get(3);
    if (actionChoice.getValue().equals("move") ||
        actionChoice.getValue().equals("unit upgrade")) {
      destChoice.getItems().addAll(board.getRegionNames(pname));

    }
    else if(actionChoice.getValue().equals("attack")) {
      for (String regionName: board.getAllRegionNames()) {
        if (board.getRegion(regionName).getOwner().getName() != pname &&
            adj(board.getRegion(regionName), pname)) {
            destChoice.getItems().add(regionName);
        }
      }
    }
  }

  // check if adjacent to a player's region
  public boolean adj(Region reg, String pname) {
    for (Region region: board.getAllRegions(pname)) {
      AdjacentChecker acheck = new AdjacentChecker(board, reg, region);
      if (acheck.isValid()) {
        return true;
      }
    }
    return false;
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    System.out.println("initialize");
    generateTabs(gui.getActiveGames());
    actionChoice.getItems().addAll("move", "attack", "unit upgrade", "tech upgrade");
    actionChoice.setValue("move");
    actionChoice.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
      chooseAction(actionChoice.getItems().get((int)newValue));
      refreshPage();
    });
  }
}
