package edu.duke.ece651.risc.client;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shared.Board;
import shared.Region;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CheckedOutputStream;


public class MainController{
    @FXML
    private BorderPane mainView;
    @FXML
    private TabPane games;
    @FXML
    private Group group;
    @FXML
    private Circle color;
    @FXML
    private Label info;

    private static Map<String, Color> colorMapper = new HashMap<>();

    static {
        colorMapper.put("blue", Color.BLUE);
        colorMapper.put("yellow", Color.YELLOW);
        colorMapper.put("green", Color.GREEN);
        colorMapper.put("red", Color.RED);
        colorMapper.put("orange", Color.ORANGE);
    }
    public void setMap(Board board) {
        List<Region> allRegions = board.getAllRegions();
        for (Region region : allRegions) {
            String owner = region.getOwner();
            String color = region.getColor();
            Circle circle = (Circle)group.lookup("#" + owner);
            circle.setFill(colorMapper.get(color));
        }
    }
    /**
     * Add
     */
    public MainController addMap(Group group) {
        this.group = group;
        mainView.setCenter(group);
        return this;
    }
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
