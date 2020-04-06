package edu.duke.ece651.risc.client;

import shared.*;
import shared.instructions.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientGUI extends Application {

  Stage window;
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
    client = new Client(configs.get(0), Integer.parseInt(configs.get(1)), Integer.parseInt(configs.get(2)));
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("RISC");

    window.setScene(loginScene());
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
        String loginValid = (String)client.receive();
        System.out.println("receive  ================================" + loginValid);
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
      window.setScene(numPlayersScene());
      activeGames+=1;
      try {
        client.joinGame();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      });
    b.setAlignment(Pos.CENTER);
    StackPane pane = new StackPane();
    pane.getChildren().add(b);
    Scene scene = new Scene(pane, 800, 600);
    return scene;
  }
  
  public Node Map() {
    //Creating an image
    Image image = new Image(getClass().getResourceAsStream("/Map_2_players.jpg"));

    //Setting the image view
    ImageView imageView = new ImageView(image);

    //Setting the position of the image
    imageView.setX(20);
    imageView.setY(50);

    //setting the fit height and width of the image view
    imageView.setFitHeight(300);
    imageView.setFitWidth(500);

    //Setting the preserve ratio of the image view
    imageView.setPreserveRatio(true);

    //Drawing a Circle
    Circle circle = new Circle();

    //Setting the properties of the circle
    circle.setCenterX(250.0f);
    circle.setCenterY(122.0f);
    circle.setRadius(10.0f);
    circle.setFill(Color.BLUE);
    //Creating a Group object
    Group root = new Group(imageView, circle);

    return root;
  }
  
  public Scene numPlayersScene() {
    Label numPlayers = new Label("Select number of players in this game:");
    ChoiceBox<Integer> numChoice = new ChoiceBox<>();
    numChoice.getItems().addAll(2, 3, 4, 5);
    Button button = new Button("Start");

    // button action
    button.setOnAction(e -> {
      try {
        client.send(numChoice.getValue());
        window.setScene(gameScene(roomBox()));
      } catch (IOException ex) {
        ex.printStackTrace();
      } catch (Exception ex1) {
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

  public Scene gameScene(HBox roomChange) throws Exception {
    
    // Instruction selection
    VBox insChange = new VBox();
    Label insLabel = new Label("Select order:");
    ChoiceBox<String> insChoice = new ChoiceBox<>();
    insChoice.getItems().addAll("Move", "Attack", "Upgrade Units", "Upgrade Technology");
    insChange.getChildren().addAll(insLabel, insChoice);

    // Instruction specs
    Label srcLabel = new Label("Source:");
    ChoiceBox<String> srcChoice = new ChoiceBox<>();
    srcChoice.getItems().addAll("placeholder1", "placeholder2");
    Label destLabel = new Label("Destination:");
    ChoiceBox<String> destChoice = new ChoiceBox<>();
    destChoice.getItems().addAll("placeholder3", "placeholder4");
    Label levelLabel = new Label("Level to operate on:");
    TextField levelText = new TextField();
    Label newLevelLabel = new Label("Level to upgrade to:");
    TextField newLevelText = new TextField();
    Label num = new Label("The number of units:");
    TextField numText = new TextField();

    // Commit changes
    Button actionButton = new Button("Add action");
    Button doneButton = new Button("Done");

    // TODO: button actions
    
    actionButton.setOnAction(e -> {
        // Move
        if (insChoice.getValue().equals("Move")) {
          Move moveIns = new Move(srcChoice.getValue(), destChoice.getValue(),
                                  Integer.parseInt(levelText.getText()), Integer.parseInt(numText.getText()));
        }
        // Attack
        else if (insChoice.getValue().equals("Attack")) {
          Attack attackIns = new Attack(srcChoice.getValue(), destChoice.getValue(),
                                  Integer.parseInt(levelText.getText()), Integer.parseInt(numText.getText()));
        }
        // Upgrade unit
        else if (insChoice.getValue().equals("Upgrade Units")) {
          UnitUpgrade upUnitIns = new UnitUpgrade("name", srcChoice.getValue(), Integer.parseInt(levelText.getText()),
                                                  Integer.parseInt(newLevelText.getText()),Integer.parseInt(numText.getText()));
        }
        // Upgrade technology
        else if (insChoice.getValue().equals("Upgrade Units")) {
          TechUpgrade upTechIns = new TechUpgrade("name", Integer.parseInt(levelText.getText()),
                                                  Integer.parseInt(newLevelText.getText()));
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
    //map
    borderPane.setCenter(Map());

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
  
}
