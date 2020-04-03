package edu.duke.ece651.risc.client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Client extends Application {

  // Button button;
  Stage window;
  //Scene scene1, scene2;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    /* TOOL2 ===== switch scene */
    // window = primaryStage;

    // Label label1 = new Label("first scene");
    // Button button1 = new Button("go to scene2");
    // button1.setOnAction(e -> window.setScene(scene2));

    // //Layout 1 - childrean laid out in vertial column
    // VBox layout1 = new VBox(20);
    // layout1.getChildren().addAll(label1, button1);
    // scene1 = new Scene(layout1, 200, 200);

    // //Button2
    // Button button2 = new Button("go to scene1");
    // button2.setOnAction(e -> window.setScene(scene1));

    // Button button3 = new Button("go to scene3");
    

    // // Layout 2
    // StackPane layout2 = new StackPane();
    // layout2.getChildren().add(button2);
    // scene2 = new Scene(layout2, 600, 300);

    // window.setScene(scene1);
    // window.setTitle("Title");
    // window.show();

    /*tool3 ===== menu */
    window = primaryStage;
    window.setTitle("RISC");

    HBox roomChange = new HBox();
    Button button1 = new Button("Room1");
    Button button2 = new Button("Room2");
    Button button3 = new Button("Room3");
    roomChange.getChildren().addAll(button1, button2, button3);

    BorderPane borderPane = new BorderPane();
    borderPane.setTop(roomChange);

    Scene scene = new Scene(borderPane, 300, 250);
    window.setScene(scene);
    window.show();
  }
}

