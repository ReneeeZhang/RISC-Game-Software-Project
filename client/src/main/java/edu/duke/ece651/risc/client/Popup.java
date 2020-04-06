package edu.duke.ece651.risc.client;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.Scene;


public class Popup {
  public static void showInfo(String info) {
    Stage stage = new Stage();
    stage.initModality(Modality.APPLICATION_MODAL);
    Label label = new Label(info);
    Scene scene = new Scene(label, 400, 200);
    stage.setScene(scene);
    stage.showAndWait();
  }
}
