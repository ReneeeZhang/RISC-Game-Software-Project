package edu.duke.ece651.risc.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shared.Board;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class TwoPlayerMapController implements Initializable {
    @FXML
    private Circle Fitzpatrick;
    @FXML
    private Circle Hudson;
    @FXML
    private Circle Wilson;
    @FXML
    private Circle Perkins;
    @FXML
    private Circle Bostock;
    @FXML
    private Circle Teer;
    private Board board;
    private static Map<String, Color> colors = new HashMap<>();

    static {
        colors.put("Player1", Color.BLUE);
        colors.put("Player2", Color.GREEN);
    }
    public void setColor(Board board) {
        this.board = board;
        Fitzpatrick.setFill(colors.get(board.getRegion("Fitzpatrick").getOwner()));
        Hudson.setFill(colors.get(board.getRegion("Hudson").getOwner()));
        Wilson.setFill(colors.get(board.getRegion("Wilson").getOwner()));
        Perkins.setFill(colors.get(board.getRegion("Perkins").getOwner()));
        Bostock.setFill(colors.get(board.getRegion("Bostock").getOwner()));
        Teer.setFill(colors.get(board.getRegion("Teer").getOwner()));
    }
    @FXML
    public void showInfo(MouseEvent event) {
        Node source = (Node)event.getSource();
        String id = source.getId();
        Popup.showInfo(board.getRegion(id).getInfo());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Fitzpatrick.setOnMouseClicked(this::showInfo);
        Hudson.setOnMouseClicked(this::showInfo);
        Wilson.setOnMouseClicked(this::showInfo);
        Perkins.setOnMouseClicked(this::showInfo);
        Bostock.setOnMouseClicked(this::showInfo);
        Teer.setOnMouseClicked(this::showInfo);
    }
}
