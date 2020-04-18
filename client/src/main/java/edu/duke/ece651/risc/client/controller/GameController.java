package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.*;
import shared.*;
import shared.instructions.*;

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
import java.util.ArrayList;

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
    window.setScene(new Scene(mainView));
  }

  @FXML
  public void doAdd() {
    String pname = gui.getCurrentName(currentRoom);

    // Move
    if (actionChoice.getValue().equals("Move")) {
      TextField src = (TextField) right.getChildren().get(1);
      TextField dest = (TextField) right.getChildren().get(3);
      TextField level = (TextField) right.getChildren().get(5);
      TextField num = (TextField) right.getChildren().get(7);
      
      Move moveIns = new Move(pname, src.getText(), dest.getText(),
                                  Integer.parseInt(level.getText()), Integer.parseInt(num.getText()));
          if(gui.getClient().isValidInst(currentRoom, moveIns)) {
              insList.add(moveIns);
              Popup.showInfo("Instruction added!");
          }
          else {
            Popup.showInfo("Invalid instruction!");
          }
              

        }
      // Attack
        else if (actionChoice.getValue().equals("Attack")) {
          TextField src = (TextField) right.getChildren().get(1);
          TextField dest = (TextField) right.getChildren().get(3);
          TextField level = (TextField) right.getChildren().get(5);
          TextField num = (TextField) right.getChildren().get(7);
      
          Attack attackIns = new Attack(pname, src.getText(), dest.getText(),
                                  Integer.parseInt(level.getText()), Integer.parseInt(num.getText()));
          if(gui.getClient().isValidInst(currentRoom, attackIns)) {
            insList.add(attackIns);
            Popup.showInfo("Instruction added!");
          }
          else {
            Popup.showInfo("Invalid instruction!");
          }
        }
      // Upgrade unit
        else if (actionChoice.getValue().equals("Unit upgrade")) {
          TextField region = (TextField) right.getChildren().get(1);
          TextField oldlevel = (TextField) right.getChildren().get(3);
          TextField newlevel = (TextField) right.getChildren().get(5);
          TextField num = (TextField) right.getChildren().get(7);
          
          UnitUpgrade upUnitIns = new UnitUpgrade(pname, region.getText(), Integer.parseInt(oldlevel.getText()),
                                                  Integer.parseInt(newlevel.getText()),Integer.parseInt(num.getText()));
          if(gui.getClient().isValidInst(currentRoom, upUnitIns)) {
            insList.add(upUnitIns);
            Popup.showInfo("Instruction added!");
          }
          else {
            Popup.showInfo("Invalid instruction!");
          }
        }
      // Upgrade technology
        else if (actionChoice.getValue().equals("Tech upgrade")) {
      
          TechUpgrade upTechIns = new TechUpgrade(pname, board.getPlayer(pname).getCurrLevel(), board.getPlayer(pname).getCurrLevel()+1);
          if(gui.getClient().isValidInst(currentRoom, upTechIns)) {
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
    gui.sendObj(currentRoom, insList);
    //System.out.println("send :" + numChoice.getValue());
    
        Board newBoard = (GameBoard) gui.receiveObj(currentRoom);
        System.out.println(newBoard.toString());
        gui.getClient().setBoard(currentRoom, newBoard);
        Popup.showInfo("Instructrion submitted.");
        insList.clear();

        // check win/lose
        if (gui.getClient().hasWon(currentRoom)) {
          gui.setWinScene();
        }
        else if (gui.getClient().hasLost(currentRoom)) {
          gui.setLoseScene(currentRoom);
        }

        // roomLabel.setText("Name: " + pname + "\n"
        //                       + "You are in Room: " + (currentRoom+1) + "\n"
        //                       + "Level: " + newBoard.getPlayer(pname).getCurrLevel() + "\n"
        //                       + "Food resource: " + newBoard.getPlayer(pname).getFoodAmount() + "\n"
        //                       + "Technology resource: " + newBoard.getPlayer(pname).getTechAmount());

        // borderPane.setTop(rooms);
        // borderPane.setRight(allIns);
        // borderPane.setCenter(mapScene(newBoard, playerNumbers.get(currentRoom)));
     
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
    System.out.println("start game " + currentRoom + 1);
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
    actionChoice.getItems().addAll("Move", "Attack", "Unit upgrade", "Tech upgrade");
    actionChoice.setValue("Move");
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
