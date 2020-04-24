package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    public TextField userName;
    @FXML
    public TextField password;
    @FXML
    public Button login;

    private ClientGUI gui;
    public LoginController(ClientGUI g) {
      this.gui = g;
    }
  
    @FXML
    public void doLogin() throws IOException {
        System.out.println(userName.getText() + "&&" + password.getText());
        String username = userName.getText();
        String pwd = password.getText();
        try {
          gui.sendStr(username + "&&" + pwd);
          String loginValid = gui.receiveStr();
          System.out.println("received: " + loginValid);
          if (loginValid.equals("yes")) {
            gui.setStartScene();
          }
          else {
            Popup.showInfo("Incorrect username or password");
            gui.setLoginScene();
          }
        } catch (Exception ex2) {
          ex2.printStackTrace();
        }
    }

}
