package edu.duke.ece651.risc.client.controller;

import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;


class LoginControllerTest {

    @Test
    void doLogin() throws IOException {
        URL resource = LoginControllerTest.class.getResource("/fxml/login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        Object load = fxmlLoader.load();
        LoginController controller = fxmlLoader.getController();
    }
}