package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.client.controller.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import shared.*;
import shared.instructions.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrintColor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientGUI extends Application {

  static Stage window;
  Client client;
  int activeGames;
  ArrayList<String> playerNames;
  ArrayList<Integer> playerNumbers;


  public static void main(String[] args) {
    launch(args);
  }

  private List<String> readConfig() {
    List<String> configs = new ArrayList<>();
    InputStream is = getClass().getResourceAsStream("/config/connection.config");
    Scanner scanner = new Scanner(is);
    while (scanner.hasNext()) {
      configs.add(scanner.nextLine());
    }
    scanner.close();
    return configs;
  }
  
  @Override
  public void init() throws Exception{
    activeGames = 0;
    List<String> configs = readConfig();
    client = new Client(configs.get(0), Integer.parseInt(configs.get(1)), Integer.parseInt(configs.get(2)));
    playerNames = new ArrayList<>();
    playerNumbers = new ArrayList<>();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("RISC");
    window.setScene(loginScene());
    window.show();
  }


  /** ========== scenes ========== */
  public Scene loginScene() {
    URL  resource = ClientGUI.class.getResource("/fxml/login.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> {
      return new LoginController(this);
      });
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new Scene(load, 800, 600);
  }


  public Scene startScene() {
    URL  resource = ClientGUI.class.getResource("/fxml/start.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> {
      return new StartController(this);
      });
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new Scene(load);
  }

  
  public Scene numPlayersScene() throws IOException {
    URL  resource = ClientGUI.class.getResource("/fxml/playerSet.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> new NumPlayersController(this));
    Parent load = fxmlLoader.load();

    return new Scene(load);
  }

  public Scene gameScene(int currentRoom) {
    
    Board board = client.getBoard(currentRoom);
    client.setBoard(currentRoom, board);

    String pname = playerNames.get(currentRoom);
    
    // check win/lose
    if (client.hasWon(currentRoom)) {
      window.setScene(winScene());
    }
    else if (client.hasLost(currentRoom)) {
      window.setScene(loseScene(currentRoom));
    }
   
    VBox rooms = new VBox();
    HBox roomChange = roomBox();
    Label roomLabel = new Label("Name: " + pname + "\n"
                                + "You are in Room: " + (currentRoom+1) + "\n"
                                + "Level: " + board.getPlayer(pname).getCurrLevel() + "\n"
                                + "Food resource: " + board.getPlayer(pname).getFoodAmount() + "\n"
                                + "Technology resource: " + board.getPlayer(pname).getTechAmount());
    rooms.getChildren().addAll(roomChange, roomLabel);
    
    // Instruction selection
    VBox insChange = new VBox();
    Label insLabel = new Label("Select order:");
    ChoiceBox<String> insChoice = new ChoiceBox<>();
    insChoice.getItems().addAll("Move", "Attack", "Upgrade Units", "Upgrade Technology");
    insChange.getChildren().addAll(insLabel, insChoice);

    // Instruction specs
    Label srcLabel = new Label("Source:");
    ChoiceBox<String> srcChoice = new ChoiceBox<>();
    for (String regionName: board.getAllRegionNames()) {
      srcChoice.getItems().add(regionName);
    }
    
    Label destLabel = new Label("Destination:");
    ChoiceBox<String> destChoice = new ChoiceBox<>();
    for (String regionName: board.getAllRegionNames()) {
      destChoice.getItems().add(regionName);
    }
    
    Label levelLabel = new Label("Level to operate on:");
    TextField levelText = new TextField();
    Label newLevelLabel = new Label("Level to upgrade to:");
    TextField newLevelText = new TextField();
    Label num = new Label("The number of units:");
    TextField numText = new TextField();

    // Commit changes
    Button actionButton = new Button("Add action");
    Button doneButton = new Button("Done");

    ArrayList<Instruction> insList = new ArrayList<>();

    // button actions
    actionButton.setOnAction(e -> {
        // Move
        if (insChoice.getValue().equals("Move") && levelText.getText() != null && numText.getText() != null) {
          Move moveIns = new Move(playerNames.get(currentRoom), srcChoice.getValue(), destChoice.getValue(),
                                  Integer.parseInt(levelText.getText()), Integer.parseInt(numText.getText()));
          if(client.isValidInst(currentRoom, moveIns)) {
              insList.add(moveIns);
              Popup.showInfo("instruction added!");
          }
          else {
            Popup.showInfo("invalid instruction!");
          }
              

        }
        // Attack
        else if (insChoice.getValue().equals("Attack") && levelText.getText() != null && numText.getText() != null) {
          Attack attackIns = new Attack(playerNames.get(currentRoom), srcChoice.getValue(), destChoice.getValue(),
                                  Integer.parseInt(levelText.getText()), Integer.parseInt(numText.getText()));
          if(client.isValidInst(currentRoom, attackIns)) {
            insList.add(attackIns);
            Popup.showInfo("instruction added!");
          }
          else {
            Popup.showInfo("invalid instruction!");
          }
        }
        // Upgrade unit
        else if (insChoice.getValue().equals("Upgrade Units") && levelText.getText() != null
                 && newLevelText.getText() != null && numText.getText() != null) {
          UnitUpgrade upUnitIns = new UnitUpgrade(pname, srcChoice.getValue(), Integer.parseInt(levelText.getText()),
                                                  Integer.parseInt(newLevelText.getText()),Integer.parseInt(numText.getText()));
          if(client.isValidInst(currentRoom, upUnitIns)) {
            insList.add(upUnitIns);
            Popup.showInfo("instruction added!");
          }
          else {
            Popup.showInfo("invalid instruction!");
          }
        }
        // Upgrade technology
        else if (insChoice.getValue().equals("Upgrade Technology") && levelText.getText() != null
                 && newLevelText.getText() != null) {
      
          TechUpgrade upTechIns = new TechUpgrade(pname, board.getPlayer(pname).getCurrLevel(), board.getPlayer(pname).getCurrLevel()+1);
          if(client.isValidInst(currentRoom, upTechIns)) {
            insList.add(upTechIns);
            Popup.showInfo("instruction added!");
          }
          else {
            Popup.showInfo("invalid instruction!");
          }
        }
        // Not filled completely
        else {
          Popup.showInfo("You need to fill all required info!");
        } 
    });


    BorderPane borderPane = new BorderPane();

    VBox allIns = new VBox();
    allIns.getChildren().addAll(insChange, srcLabel, srcChoice, destLabel, destChoice,
                                levelLabel, levelText, newLevelLabel, newLevelText,
                                num, numText, actionButton, doneButton);
    
    // commit instructions
    doneButton.setOnAction(e -> {
      try {
        client.sendViaChannel(currentRoom, insList);
      //System.out.println("send :" + numChoice.getValue());
        Board newBoard = (GameBoard) client.receiveViaChannel(currentRoom);
        System.out.println(newBoard.toString());
        client.setBoard(currentRoom, newBoard);
        Popup.showInfo("Instructrion submitted.");
        insList.clear();

        // check win/lose
        if (client.hasWon(currentRoom)) {
          window.setScene(winScene());
        }
        else if (client.hasLost(currentRoom)) {
          window.setScene(loseScene(currentRoom));
        }

        roomLabel.setText("Name: " + pname + "\n"
                              + "You are in Room: " + (currentRoom+1) + "\n"
                              + "Level: " + newBoard.getPlayer(pname).getCurrLevel() + "\n"
                              + "Food resource: " + newBoard.getPlayer(pname).getFoodAmount() + "\n"
                              + "Technology resource: " + newBoard.getPlayer(pname).getTechAmount());

        borderPane.setTop(rooms);
        borderPane.setRight(allIns);
        borderPane.setCenter(mapScene(newBoard, playerNumbers.get(currentRoom)));
      } catch (IOException ex) {
        ex.printStackTrace();
      } catch (ClassNotFoundException ex1){
        ex1.printStackTrace();
      }
               
     
    });

        

    // Overall layout
    borderPane.setTop(rooms);
    borderPane.setRight(allIns);
    borderPane.setCenter(mapScene(board, playerNumbers.get(currentRoom)));
    Scene scene = new Scene(borderPane, 800, 600);

    return scene;
  }

  public static Node mapScene(Board board, int playerNumber) {
    URL resource;
    Parent load = null;
    try {
    if (playerNumber == 2) {
      resource = ClientGUI.class.getResource("/fxml/twoPlayerMap.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(resource);
      load = fxmlLoader.load();
      TwoPlayerMapController controller = fxmlLoader.getController();
      controller.setColor(board);
    }
    else if(playerNumber == 3) {
      resource = ClientGUI.class.getResource("/fxml/threePlayerMap.fxml");
      FXMLLoader fxmlLoader = new FXMLLoader();
      fxmlLoader.setLocation(resource);
      load = fxmlLoader.load();
      ThreePlayerMapController controller = fxmlLoader.getController();
      controller.setColor(board);
    }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return load;
  }
                           
  public Scene winScene() {
    URL  resource = ClientGUI.class.getResource("/fxml/win.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new Scene(load, 800, 600);
  }

                           
  public Scene loseScene(int currentRoom) {
    URL  resource = ClientGUI.class.getResource("/fxml/lose.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> {
        return new LoseController(this, currentRoom);
      });
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new Scene(load, 800, 600);
  }

  public Scene watchScene(int currentRoom) {
    HBox roomChange = roomBox();
    VBox winOption = new VBox();
    Button button = new Button("Play again");
    button.setOnAction(e -> {
      try {
        window.setScene(numPlayersScene());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
    
    BorderPane borderPane = new BorderPane();
    borderPane.setLeft(roomChange);
    

    Button refresh = new Button("Refresh");
    refresh.setOnAction(e -> {
        try {
            client.sendViaChannel(currentRoom, new ArrayList<Instruction>());
            Board newBoard = (GameBoard) client.receiveViaChannel(currentRoom);
            client.setBoard(currentRoom, newBoard);
            if (!client.isGameOver(currentRoom)) {
            
              borderPane.setCenter(mapScene(newBoard, playerNumbers.get(currentRoom)));
          }
          else {
            Popup.showInfo("Game over");
            refresh.setDisable(true);
          }
        } catch (IOException ex) {
          ex.printStackTrace();
        } catch (ClassNotFoundException ex1) {
          ex1.printStackTrace();
        }
      });

    winOption.getChildren().add(refresh);
    borderPane.setRight(winOption);
    
    
    Scene scene = new Scene(borderPane, 800, 600);
    return scene;
  }


  /* ========== Elements in gameScene ========== */
  public HBox roomBox() {
    // Rome switch
    HBox roomChange = new HBox();
    Button button1 = new Button("Room1");
    Button button2 = new Button("Room2");
    Button button3 = new Button("Room3");
    Button button4 = new Button("Start new game");

    // action
    button1.setOnAction(e -> window.setScene(gameScene(0)));
    button2.setOnAction(e -> {
        if (activeGames > 1) {
          window.setScene(gameScene(1));
        }
        else {
          Popup.showInfo("You need start a new game to access this room");
        }
    });
    button3.setOnAction(e -> {

        if (activeGames > 2) {
          window.setScene(gameScene(2));
        }
        else {
          Popup.showInfo("You need start a new game to access this room");
        }
    });
    
    button4.setOnAction(e -> {
        if (activeGames < 3) {
          // start new game
          try {
            client.joinGame();
            window.setScene(numPlayersScene());
          }
          catch (IOException ex) {
            ex.printStackTrace();
          }
        }
        else {
          Popup.showInfo("You can have 3 games at most!");
        }
      });
    
    roomChange.getChildren().addAll(button1, button2, button3, button4);
    return roomChange;
  }

  /* ========== Getters  ========== */
  public Client getClient() {
    return client;
  }

  public int getActiveGames() {
    return activeGames;
  }

  public ArrayList<String> getPlayerNames() {
    return playerNames;
  }
  
  public ArrayList<Integer> getPlayerNumbers() {
    return playerNumbers;
  }

  public String getCurrentName(int room) {
    return playerNames.get(room);
  }

  public int getCurrentNumPlayers(int room) {
    return playerNumbers.get(room);
  }
    
  /* ========== Setters ========== */
  public void setLoginScene() {
    window.setScene(loginScene());
  }

  public void setStartScene() {
    window.setScene(startScene());
  }
  
  public void setNumPlayersScene() throws IOException {
    window.setScene(numPlayersScene());
  }

  public void setGameScene(int room)  {
//    window.setScene(gameScene(room));
    window.setScene(game(room));
  }

  public void setWatchScene(int room) {
    window.setScene(watchScene(room));
  }

  public void setWinScene() {
    window.setScene(winScene());
  }

  public void setLoseScene(int room) {
    window.setScene(loseScene(room));
  }

  /* ========== Adders  ========== */
  public void addActiveGame() {
    activeGames++;
  }

  public void addPlayerName(String name) {
    playerNames.add(name);
  }

  public void addPlayerNumber(int num) {
    playerNumbers.add(num);
  }
  
  /* ========== Send and receive ========== */
  public void sendStr(String str) {
    try {
      client.send(str);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String receiveStr() {
    String ans = new String();
    try {
      ans = (String) client.receive();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return ans;
  }

  // send string with room
  public void sendStr(int room, String str) {
    try {
      client.sendViaChannel(room, str);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
  }
  
  // receive string with room
  public String receiveStr(int room) {
    String ans = new String();
    try {
      ans = (String) client.receiveViaChannel(room);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
    return ans;
  }

  // send with room
  public void sendObj(int room, Object obj) {
    try {
      client.sendViaChannel(room, obj);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
  }

  // receive obj with room
  public Object receiveObj(int room) {
    Object ans = new Object();
    try {
      ans = client.receiveViaChannel(room);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
    return ans;
  }

  public Scene game(int currentRoom) {
    Group map = null;
    try {
      map = FXMLLoader.load(getClass().getResource("/fxml/twoPlayerMap.fxml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    URL resource = getClass().getResource("/fxml/game.fxml");
    FXMLLoader gameLoader = new FXMLLoader();
    gameLoader.setLocation(resource);
    gameLoader.setControllerFactory(c -> new GameController(this));
    BorderPane load = null;
    try {
      load = gameLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameController controller = gameLoader.getController();
    controller.setCurrentRoom(currentRoom).addBoard(client.getBoard(currentRoom - 1)).addMap(map);
    return new Scene(load);
  }

  
}
