package edu.duke.ece651.risc.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;



public class MainController {
    ClientGUI main;
    @FXML
    private TabPane games;
    @FXML
    private ImageView map;
    @FXML
    private Circle color;
    @FXML
    private Label info;
    @FXML
    public void createMove() {
        System.out.println("Move");
    }
    @FXML
    public void createAttack() {
        System.out.println("Attack");
    }
    @FXML
    public void createUpgrade() {
        System.out.println("Upgrade");
    }
    @FXML
    public void doDone() {
        System.out.println("Done");
    }

}
