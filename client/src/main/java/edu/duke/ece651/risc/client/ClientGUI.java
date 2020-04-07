package edu.duke.ece651.risc.client;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import shared.*;
import shared.instructions.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("RISC");

//    HBox roomChange = roomBox();
//    BorderPane borderPane = new BorderPane();
//    borderPane.setTop(roomChange);
//    borderPane.setCenter(mapScene(new GameBoard()));
//    window.setScene(new Scene(borderPane, 900, 600));
    window.setScene(loginScene());
    window.show();
  }


  /** ========== scenes ========== */
  public Scene loginScene() {
    GridPane grid = new GridPane();
    grid.setPadding(new Insets(20, 20, 20, 20));
    grid.setHgap(10);
    grid.setVgap(10);
    Label user = new Label("User Name");
    GridPane.setConstraints(user, 0, 0);
    TextField userText = new TextField();
    userText.setPromptText("user name");
    GridPane.setConstraints(userText, 1, 0);

    Label password = new Label("Password");
    GridPane.setConstraints(password, 0, 1);
    TextField pwdText = new TextField();
    pwdText.setPromptText("password");
    GridPane.setConstraints(pwdText, 1, 1);

    Button login = new Button("Log in");
    login.setOnAction(e -> {
      String userName = userText.getText();
      String pwd = pwdText.getText();
      try {
        client.send(userName + "&&" + pwd);
        String loginValid = (String)client.receive();
        if (loginValid.equals("yes")) {
          window.setScene(startScene());
        }
        else {
          Popup.showInfo("Incorrect username or password");
          window.setScene(loginScene());
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      } catch (ClassNotFoundException ex1) {
        ex1.printStackTrace();
      } catch (Exception ex2) {
        ex2.printStackTrace();
      }
      
    });
    
    GridPane.setConstraints(login, 1, 2);
    grid.getChildren().addAll(user, userText, password, pwdText, login);

    BorderPane layout = new BorderPane();
    layout.setCenter(grid);
    return new Scene(layout, 800, 600);
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
    
    // check win/lose
    if (client.hasWon(currentRoom)) {
      window.setScene(winScene());
    }
    else if (client.hasLost(currentRoom)) {
      window.setScene(loseScene());
    }
   
    
    HBox roomChange = roomBox();
    
    // Instruction selection
    VBox insChange = new VBox();
    Label insLabel = new Label("Select order:");
    ChoiceBox<String> insChoice = new ChoiceBox<>();
    insChoice.getItems().addAll("Move", "Attack", "Upgrade Units", "Upgrade Technology");
    insChange.getChildren().addAll(insLabel, insChoice);

    // Instruction specs
    Label srcLabel = new Label("Source:");
    ChoiceBox<String> srcChoice = new ChoiceBox<>();
    for (String regionName: board.getRegionNames(playerNames.get(currentRoom))) {
      srcChoice.getItems().add(regionName);
    }
    
    Label destLabel = new Label("Destination:");
    ChoiceBox<String> destChoice = new ChoiceBox<>();
    for (String regionName: board.getRegionNames(playerNames.get(currentRoom))) {
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
        if (insChoice.getValue().equals("Move")) {
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
        else if (insChoice.getValue().equals("Attack")) {
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
        else if (insChoice.getValue().equals("Upgrade Units")) {
          UnitUpgrade upUnitIns = new UnitUpgrade(playerNames.get(currentRoom), srcChoice.getValue(), Integer.parseInt(levelText.getText()),
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
        else if (insChoice.getValue().equals("Upgrade Units")) {
          TechUpgrade upTechIns = new TechUpgrade(playerNames.get(currentRoom), Integer.parseInt(levelText.getText()),
                                                  Integer.parseInt(newLevelText.getText()));
          if(client.isValidInst(currentRoom, upTechIns)) {
            insList.add(upTechIns);
            Popup.showInfo("instruction added!");
          }
          else {
            Popup.showInfo("invalid instruction!");
          }
        }

        
    });

    // commit instructions
    doneButton.setOnAction(e -> {
      try {
        client.sendViaChannel(currentRoom, insList);
      //System.out.println("send :" + numChoice.getValue());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
     
    });

        
    // All instruction related display
    VBox allIns = new VBox();
    allIns.getChildren().addAll(insChange, srcLabel, srcChoice, destLabel, destChoice,
                                levelLabel, levelText, newLevelLabel, newLevelText,
                                num, numText, actionButton, doneButton);

    // Overall layout
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(roomChange);
    borderPane.setRight(allIns);
    borderPane.setCenter(mapScene(board));
    Scene scene = new Scene(borderPane, 800, 600);

    return scene;
  }

  public Node mapScene(Board board) throws IOException {
    URL resource = getClass().getResource("/fxml/twoPlayerMap.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    Parent load = fxmlLoader.load();
    TwoPlayerMapController controller = fxmlLoader.getController();
    controller.setColor(board);

    return load;
  }
                           
  public Scene winScene() {
    HBox roomChange = roomBox();
    VBox winOption = new VBox();
    Button button1 = new Button("Play again");
    Button button2 = new Button("Exit");

    // button actions
    button1.setOnAction(e -> {
      try {
        window.setScene(numPlayersScene());
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
      });
    button2.setOnAction(e -> {
        try {
          window.close();
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
      });
    
    winOption.getChildren().addAll(button1, button2);
    
    BorderPane borderPane = new BorderPane();
    borderPane.setLeft(roomChange);
    borderPane.setRight(winOption);

    Scene scene = new Scene(borderPane, 800, 600);
    return scene;
  }

                           
  public Scene loseScene() {
    HBox roomChange = roomBox();
    VBox loseOption = new VBox();
    Button button1 = new Button("Watch the game");
    Button button2 = new Button("Play again");
    Button button3 = new Button("Exit");

    button1.setOnAction(e -> {
      try {
          window.setScene(watchScene());
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
      });
    button2.setOnAction(e -> {
        try {
          window.setScene(numPlayersScene());
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
      });
    button3.setOnAction(e -> {
        try {
          window.close();
      }
      catch(Exception ex) {
        ex.printStackTrace();
      }
      });

    loseOption.getChildren().addAll(button1, button2, button3);
    
    BorderPane borderPane = new BorderPane();
    borderPane.setLeft(roomChange);
    borderPane.setRight(loseOption);

    Scene scene = new Scene(borderPane, 800, 600);
    return scene;
  }

  public Scene watchScene() {
    HBox roomChange = roomBox();
    Button button = new Button("Exit");
    button.setOnAction(e -> {
      try {
        window.setScene(numPlayersScene());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
    BorderPane borderPane = new BorderPane();
    borderPane.setLeft(roomChange);
    borderPane.setRight(button);
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
    button1.setOnAction(e -> {
      try {
        window.setScene(gameScene(1));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    button1.setOnAction(e -> {
      try {
        window.setScene(gameScene(2));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    
    button4.setOnAction(e -> {
        if (activeGames < 3) {
          // start new game
          try {
            window.setScene(numPlayersScene());
            activeGames+=1;
            client.joinGame();
          }
          catch (IOException ex) {
            ex.printStackTrace();
          }
        }
        else {
          Popup.showInfo("You can have 3 games at most!");
        }
      });

    // button usability
    if (activeGames == 1) {
      button2.setDisable(true);
      button3.setDisable(true);
    }
    else if (activeGames == 2) {
      button3.setDisable(true);
    }
    
    
    roomChange.getChildren().addAll(button1, button2, button3, button4);
    return roomChange;
  }

//  public static void switchToMain() throws IOException {
//    Parent root = FXMLLoader.load(ClientGUI.class.getResource("/fxml/main.fxml"));
//    Scene scene = new Scene(root, 900, 600);
//    window.setScene(scene);
//  }
  
}
