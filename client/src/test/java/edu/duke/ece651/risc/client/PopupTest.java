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
public class PopupTest {


    @Start
    private void start(Stage stage) {
      Popup p = new Popup();
    }

    @Test
    void test() {
      
    }
    
}
