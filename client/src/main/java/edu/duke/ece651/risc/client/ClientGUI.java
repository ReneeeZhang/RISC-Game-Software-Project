package edu.duke.ece651.risc.client;

import shared.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientGUI extends Application {

  Stage window;
  Client client;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void init() throws Exception{
    //client = new Client("localhost", 6666);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("RISC");

    window.setScene(numPlayersScene());
    window.show();
  }

  public Scene loginScene() throws Exception {
    BorderPane borderPane = new BorderPane();
    Scene scene = new Scene(borderPane, 600, 400);
    return scene;
  }

  public Scene numPlayersScene() throws Exception {
    Label numPlayers = new Label("Select number of players in this game:");
    ChoiceBox<Integer> numChoice = new ChoiceBox<>();
    numChoice.getItems().addAll(2, 3, 4, 5);
    Button button = new Button("Start");

    // TODO: set on action -> set player number
    
    VBox box = new VBox();
    box.getChildren().addAll(numPlayers, numChoice, button);
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(box);
    Scene scene = new Scene(stackPane, 600, 400);
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

    // Listen for changes
    srcChoice.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> System.out.println(newValue));
    destChoice.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> System.out.println(newValue));

    // Commit changes
    Button actionButton = new Button("Add action");
    Button doneButton = new Button("Done");

    // All instruction related display
    VBox allIns = new VBox();
    allIns.getChildren().addAll(insChange, srcLabel, srcChoice, destLabel, destChoice, actionButton, doneButton);

    // Overall layout
    BorderPane borderPane = new BorderPane();
    borderPane.setTop(roomChange);
    borderPane.setRight(allIns);

    Scene scene = new Scene(borderPane, 600, 400);
    return scene;
  }

  public Scene winScene(HBox roomChange) throws Exception {
    VBox winOption = new VBox();
    Button button1 = new Button("Play again");
    Button button2 = new Button("Exit");

    // TODO: actions
    
    winOption.getChildren().addAll(button1, button2);
    
    BorderPane borderPane = new BorderPane();
    borderPane.setRight(winOption);

    Scene scene = new Scene(borderPane, 600, 400);
    return scene;
  }

  public Scene loseScene(HBox roomChange) throws Exception {
    VBox loseOption = new VBox();
    Button button1 = new Button("Watch the game");
    Button button2 = new Button("Exit");

    // TODO: actions
    
    loseOption.getChildren().addAll(button1, button2);
    
    BorderPane borderPane = new BorderPane();
    borderPane.setRight(loseOption);

    Scene scene = new Scene(borderPane, 600, 400);
    return scene;
  }


  /* ========== Elements in gameScene ========== */
  public HBox roomBox() {
    // Rome switch
    HBox roomChange = new HBox();
    Button button1 = new Button("Room1");
    Button button2 = new Button("Room2");
    Button button3 = new Button("Room3");

    // TODO: action: switch gameJoiner
    
    roomChange.getChildren().addAll(button1, button2, button3);
    return roomChange;
  }
 
}
