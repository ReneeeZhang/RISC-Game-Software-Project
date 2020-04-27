package edu.duke.ece651.risc.client.controller;

import edu.duke.ece651.risc.client.ClientGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
public class GameControllerTest {

    @Start
    private void start(Stage stage) {
        ClientGUI mock = mock(ClientGUI.class);
        URL resource = ClientGUI.class.getResource("/fxml/game.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(c -> new GameController(mock));
        fxmlLoader.setLocation(resource);
        Parent load = null;
        try {
            load = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(load));
        stage.show();
    }

    /**
     * @param robot - Will be injected by the test runner.
     */
    @Test
    void should_contain_button_with_text(FxRobot robot) {
        FxAssert.verifyThat("#add", LabeledMatchers.hasText("Add"));
        FxAssert.verifyThat("#done", LabeledMatchers.hasText("Done"));
        robot.clickOn("#add");
        robot.clickOn("#done");
    }
}