package edu.duke.ece651.risc.client;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shared.Board;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapScene {

    private static Map<String, Circle> twoPlayerMapCircle;
    public static Node createMapView(Image image) {

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
        Collection<Circle> circles = twoPlayerMapCircle.values();
        //Creating a Group object
        Group root = new Group(imageView);
        root.getChildren().addAll(circles);
        return root;
    }

    public static Node createTwoPlayerMap(Board board) {
        //Creating an image
        Image image = new Image(MapScene.class.getResourceAsStream("/Map_2_players.jpg"));
        String[] regions = {"Fitzpatrick", "Perkins", "Bostock", "Hudson", "Wilson", "Teer"};
        if (twoPlayerMapCircle == null) {
            twoPlayerMapCircle = new HashMap<>();
            Circle Fiz = new Circle(250.0f, 122.0f, 10.0f, Color.BLUE);
        }
        return createMapView(image);
    }
}
