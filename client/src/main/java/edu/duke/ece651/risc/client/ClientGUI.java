package edu.duke.ece651.risc.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientGUI extends Application {

  Stage window;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("RISC");

    window.setScene(gameScene());
    window.show();
  }

  public Scene loginScene() throws Exception {
    BorderPane borderPane = new BorderPane();
    Scene scene = new Scene(borderPane, 600, 400);
    return scene;
  }

  public Scene gameScene() throws Exception {
    // Rome switch
    HBox roomChange = new HBox();
    Button button1 = new Button("Room1");
    Button button2 = new Button("Room2");
    Button button3 = new Button("Room3");
    roomChange.getChildren().addAll(button1, button2, button3);

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
  
}

