package edu.duke.ece651.risc.client;

import edu.princeton.cs.algs4.In;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shared.Board;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ThreePlayerMapController implements Initializable {

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
    @FXML
    private Circle Lily;
    @FXML
    private Circle Oldchem;
    @FXML
    private Circle Grosshall;
    public Board board;
    private static Map<String, Color> colors = new HashMap<>();

    static {
        colors.put("Player1", Color.BLUE);
        colors.put("Player2", Color.GREEN);
        colors.put("Player3", Color.RED);
    }
    public void setColor(Board board) {
        this.board = board;
        Fitzpatrick.setFill(colors.get(board.getRegion("Fitzpatrick").getOwner()));
        Hudson.setFill(colors.get(board.getRegion("Hudson").getOwner()));
        Wilson.setFill(colors.get(board.getRegion("Wilson").getOwner()));
        Perkins.setFill(colors.get(board.getRegion("Perkins").getOwner()));
        Bostock.setFill(colors.get(board.getRegion("Bostock").getOwner()));
        Teer.setFill(colors.get(board.getRegion("Teer").getOwner()));
        Lily.setFill(colors.get(board.getRegion("Lily").getOwner()));
        Oldchem.setFill(colors.get(board.getRegion("Oldchem").getOwner()));
        Grosshall.setFill(colors.get(board.getRegion("Grosshall").getOwner()));
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
        Lily.setOnMouseClicked(this::showInfo);
        Oldchem.setOnMouseClicked(this::showInfo);
        Grosshall.setOnMouseClicked(this::showInfo);
    }
}


