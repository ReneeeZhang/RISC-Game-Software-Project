package edu.duke.ece651.risc.client;

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
public class ClientGUITest {


    @Start
    private void start(Stage stage) {
        ClientGUI gui = new ClientGUI();
        stage.setScene(gui.loginScene());
        try {
        stage.setScene(gui.numPlayersScene());
        } catch (IOException e) {
        }
        stage.setScene(gui.loseScene(0));
        stage.setScene(gui.watchScene(0));
        stage.setScene(gui.winScene());
        stage.setScene(gui.startScene());
        stage.show();
        gui.addActiveGame();
    }

    @Test
    void test() {
     
    }
    
}
