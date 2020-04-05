package edu.duke.ece651.risc.client;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login {

    public static boolean display() {
        boolean res = true;
        Stage window = new Stage();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setHgap(10);
        grid.setVgap(10);
        Label user = new Label("User Name");
        GridPane.setConstraints(user, 0, 0);
        TextField userText = new TextField();
        userText.setPromptText("user name");
        GridPane.setConstraints(userText, 1, 0);

        Label password = new Label("Password");
        GridPane.setConstraints(password, 0, 1);
        TextField pwdText = new TextField();
        pwdText.setPromptText("password");
        GridPane.setConstraints(pwdText, 1, 1);

        Button login = new Button("Log in");
        GridPane.setConstraints(login, 1, 2);

        grid.getChildren().addAll(user, userText, password, pwdText, login);

        BorderPane layout = new BorderPane();
        layout.setCenter(grid);

        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        window.show();
        return res;
    }
}
