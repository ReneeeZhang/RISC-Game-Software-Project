package edu.duke.ece651.risc.client;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
    //Client client = new Client(configs.get(0), Integer.parseInt(configs.get(1)), Integer.parseInt(configs.get(2)));
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("RISC");
//
//    window.setScene(gameScene(roomBox()));

    Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

    Scene scene = new Scene(root, 900, 600);
    window.setScene(scene);
    window.show();
  }


  /** ========== scenes ========== */
  public Scene loginScene() throws Exception {
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
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    GridPane.setConstraints(login, 1, 2);

    grid.getChildren().addAll(user, userText, password, pwdText, login);

    BorderPane layout = new BorderPane();
    layout.setCenter(grid);
    return new Scene(layout, 800, 600);
  }

  public Scene numPlayersScene() throws Exception {
    Label numPlayers = new Label("Select number of players in this game:");
    ChoiceBox<Integer> numChoice = new ChoiceBox<>();
    numChoice.getItems().addAll(2, 3, 4, 5);
    Button button = new Button("Start");

    // button action
    button.setOnAction(e -> {
      try {
        client.send(numChoice.getValue());
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });
    
    VBox box = new VBox();
    box.getChildren().addAll(numPlayers, numChoice, button);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(box);
    Scene scene = new Scene(stackPane, 800, 600);
    return scene;
  }

  public Scene gameScene(HBox roomChange) throws Exception {
    
    // Instruction selection
    HBox insChange = new HBox();
    Button button4 = new Button("Move");
    Button button5 = new Button("Attack");
    Button button6 = new Button("Upgrade");
    insChange.getChildren().addAll(button4, button5, button6);

    // Instruction specs
    Label srcLabel = new Label("Source");
    ChoiceBox<String> srcChoice = new ChoiceBox<>();
    srcChoice.getItems().addAll("placeholder1", "placeholder2");
    Label destLabel = new Label("Destination");
    ChoiceBox<String> destChoice = new ChoiceBox<>();
    destChoice.getItems().addAll("placeholder3", "placeholder4");
    Label level = new Label("Level to operate on:");
    TextField levelText = new TextField();
    Label num = new Label("The number of units:");
    TextField numText = new TextField();

    // Commit changes
    Button actionButton = new Button("Add action");
    Button doneButton = new Button("Done");

    // TODO: button actions

    // All instruction related display
    VBox allIns = new VBox();
    allIns.getChildren().addAll(insChange, srcLabel, srcChoice, destLabel, destChoice,
                                level, levelText, num, numText, actionButton, doneButton);

    // Overall layout
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(roomChange);
    borderPane.setRight(allIns);

    Scene scene = new Scene(borderPane, 800, 600);

    return scene;
  }

  public Scene winScene(HBox roomChange) throws Exception {
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
    borderPane.setRight(winOption);

    Scene scene = new Scene(borderPane, 800, 600);
    return scene;
  }

  public Scene loseScene(HBox roomChange) throws Exception {
    VBox loseOption = new VBox();
    Button button1 = new Button("Watch the game");
    Button button2 = new Button("Play again");
    Button button3 = new Button("Exit");

    button1.setOnAction(e -> {
      try {
          window.setScene(watchScene(roomBox()));
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
    borderPane.setRight(loseOption);

    Scene scene = new Scene(borderPane, 800, 600);
    return scene;
  }

  public Scene watchScene(HBox roomChange) throws Exception {
    Button button = new Button("Exit");
    button.setOnAction(e -> {
      try {
        window.setScene(numPlayersScene());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
    BorderPane borderPane = new BorderPane();
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

    // TODO: action
    button4.setOnAction(e -> {
        if (activeGames < 3) {
          // start new game
          try {
            client.joinGame();
            activeGames++;
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
    if (activeGames == 0) {
      button1.setDisable(true);
      button2.setDisable(true);
      button3.setDisable(true);
    }
    else if (activeGames == 1) {
      button2.setDisable(true);
      button3.setDisable(true);
    }
    else if (activeGames == 2) {
      button3.setDisable(true);
    }
    
    
    roomChange.getChildren().addAll(button1, button2, button3, button4);
    return roomChange;
  }

  public static void switchToMain() throws IOException {
    Parent root = FXMLLoader.load(ClientGUI.class.getResource("/fxml/main.fxml"));
    Scene scene = new Scene(root, 900, 600);
    window.setScene(scene);
  }
  
}
