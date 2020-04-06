package edu.duke.ece651.risc.client;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shared.Board;

public class MapScene {

    public static Node createTwoPlayerMap(Board board) {
        //Creating an image
        Image image = new Image(MapScene.class.getResourceAsStream("/Map_2_players.jpg"));
        String[] regions = {"Fitzpatrick", "Perkins", "Bostock", "Hudson", "Wilson", "Teer"};
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

    enum MapCircle {

        ;
        float centerX;
        float centerY;
        float radius;
        Color color;

        MapCircle(float centerX, float centerY, float radius, Color color) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
            this.color = color;
        }
    }
}
