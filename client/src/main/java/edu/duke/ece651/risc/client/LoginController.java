package edu.duke.ece651.risc.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {
    ClientGUI main;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    public void doLogin() throws IOException {
        System.out.println(userName.getText() + "&&" + password.getText());
        switchToMain();
    }

    private void switchToMain() throws IOException {
        main.switchToMain();
    }

}
