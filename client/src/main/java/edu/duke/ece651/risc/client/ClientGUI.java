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
    Button b = new Button("Start a new game");
    b.setOnAction(e -> {
      
      try {
        client.joinGame();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      window.setScene(numPlayersScene());
      });
    b.setAlignment(Pos.CENTER);
    StackPane pane = new StackPane();
    pane.getChildren().add(b);
    Scene scene = new Scene(pane, 800, 600);
    return scene;
  }
  

  
  public Scene numPlayersScene() {

    // display
    Label numPlayers = new Label("Select number of players in this game:");
    ChoiceBox<Integer> numChoice = new ChoiceBox<>();
    numChoice.getItems().addAll(2, 3, 4, 5);
    Button button = new Button("Start");

    // button action
    button.setOnAction(e -> {
      try {
        client.sendViaChannel(activeGames, numChoice.getValue());
        System.out.println("send :" + numChoice.getValue());
      } catch (IOException ex) {
        ex.printStackTrace();
      } catch (Exception ex1) {
        ex1.printStackTrace();
      }

      try {
        // Get player name and board
        String pName = new String();
        Board board = new GameBoard();
        
        // add name to list
        pName = (String) client.receiveViaChannel(activeGames);
        System.out.println("receive player name: " + pName);
        playerNames.add(pName);
        playerNumbers.add(numChoice.getValue());
        // init game
        board = (GameBoard) client.receiveViaChannel(activeGames);
        System.out.println("receive board: ");

        client.initMatch(activeGames, playerNames.get(activeGames), board);
        System.out.println("game inited");
        // increment active game count
        window.setScene(gameScene(activeGames));
        activeGames+=1;
        System.out.println("Avtive games = " + activeGames);
      } catch (IOException ex) {
        ex.printStackTrace();
      } catch (ClassNotFoundException ex1) {
        ex1.printStackTrace();
      }
    });

    
    VBox box = new VBox();
    box.getChildren().addAll(numPlayers, numChoice, button);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(box);
    Scene scene = new Scene(stackPane, 800, 600);
    return scene;
  }

  public Scene gameScene(int currentRoom) throws IOException {
    
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
          Move moveIns = new Move(srcChoice.getValue(), destChoice.getValue(),
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
          Attack attackIns = new Attack(srcChoice.getValue(), destChoice.getValue(),
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

  public static Node mapScene(Board board, int playerNumber) throws IOException {
    URL resource;
    Parent load = null;
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

    return load;
  }
                           
  public Scene winScene() {
    HBox roomChange = roomBox();
    VBox winOption = new VBox();
    
    BorderPane borderPane = new BorderPane();
    borderPane.setLeft(roomChange);
    borderPane.setRight(winOption);

    Label win = new Label("You win");
    win.setAlignment(Pos.CENTER);
    borderPane.setCenter(win);

    Scene scene = new Scene(borderPane, 800, 600);
    return scene;
  }

                           
  public Scene loseScene(int currentRoom) {
    HBox roomChange = roomBox();
    VBox loseOption = new VBox();
    Button button1 = new Button("Watch the game");
    
    button1.setOnAction(e -> {
      try {
          window.setScene(watchScene(currentRoom));
          client.sendViaChannel(currentRoom, "yes");
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
      });   

    loseOption.getChildren().add(button1);
    
    BorderPane borderPane = new BorderPane();
    borderPane.setLeft(roomChange);
    borderPane.setRight(loseOption);

    Label lose = new Label("You lost");
    lose.setAlignment(Pos.CENTER);
    borderPane.setCenter(lose);

    Scene scene = new Scene(borderPane, 800, 600);
    return scene;
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
    button1.setOnAction(e -> {
      try {
        window.setScene(gameScene(0));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    button2.setOnAction(e -> {
      try {
        if (activeGames > 1) {
          window.setScene(gameScene(1));
        }
        else {
          Popup.showInfo("You need start a new game to access this room");
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    button3.setOnAction(e -> {
      try {
        if (activeGames > 2) {
          window.setScene(gameScene(2));
        }
        else {
          Popup.showInfo("You need start a new game to access this room");
        }
      } catch (IOException ex) {
        ex.printStackTrace();
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


  /* ========== Scene setters ========== */
  public void setLoginScene() {
    window.setScene(loginScene());
  }

  public void setStartScene() {
    window.setScene(startScene());
  }
  
  public void setNumPlayersScene() {
    window.setScene(numPlayersScene());
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

  public void sendObj(int room, Object obj) {
    try {
      client.sendViaChannel(room, obj);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
  }

  public String receiveStr(int room) {
    String ans = new String();
    return ans;
  }

  

//  public static void switchToMain() throws IOException {
//    Parent root = FXMLLoader.load(ClientGUI.class.getResource("/fxml/game.fxml"));
//    Scene scene = new Scene(root, 900, 600);
//    window.setScene(scene);
//  }
  
}
