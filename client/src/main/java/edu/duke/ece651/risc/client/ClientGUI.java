package edu.duke.ece651.risc.client;

import edu.duke.ece651.risc.client.controller.*;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import shared.*;
import shared.instructions.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrintColor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientGUI extends Application {

  static Stage window;
  Client client;
  int activeGames;
  ArrayList<String> playerNames;
  ArrayList<Integer> playerNumbers;


  public static void main(String[] args) {
    launch(args);
  }

  private List<String> readConfig() {
    List<String> configs = new ArrayList<>();
    InputStream is = getClass().getResourceAsStream("/config/connection.config");
    Scanner scanner = new Scanner(is);
    while (scanner.hasNext()) {
      configs.add(scanner.nextLine());
    }
    scanner.close();
    return configs;
  }
  
  @Override
  public void init() throws Exception{
    activeGames = 0;
    List<String> configs = readConfig();
    client = new Client(configs.get(0), Integer.parseInt(configs.get(1)), Integer.parseInt(configs.get(2)), Integer.parseInt(configs.get(3)));
    playerNames = new ArrayList<>();
    playerNumbers = new ArrayList<>();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    window = primaryStage;
    window.setTitle("RISC");
    window.setScene(loginScene());
    window.show();
  }


  /** ========== scenes ========== */
  public Scene loginScene() {
    URL  resource = ClientGUI.class.getResource("/fxml/login.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> {
      return new LoginController(this);
      });
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new Scene(load, 800, 600);
  }


  public Scene startScene() {
    URL  resource = ClientGUI.class.getResource("/fxml/start.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> {
      return new StartController(this);
      });
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new Scene(load);
  }

  
  public Scene numPlayersScene() throws IOException {
    URL  resource = ClientGUI.class.getResource("/fxml/playerSet.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> new NumPlayersController(this));
    Parent load = fxmlLoader.load();

    return new Scene(load);
  }
                           
  public Scene winScene() {
    URL  resource = ClientGUI.class.getResource("/fxml/win.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new Scene(load);
  }

                           
  public Scene loseScene(int currentRoom) {
    URL resource = ClientGUI.class.getResource("/fxml/lose.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> {
        return new LoseController(this, currentRoom);
      });
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new Scene(load);
  }

  public Scene watchScene(int currentRoom) {
    URL  resource = ClientGUI.class.getResource("/fxml/watch.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(resource);
    fxmlLoader.setControllerFactory(c -> {
        return new WatchController(this);
      });
    Parent load = null;
    try {
      load = fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return new Scene(load, 800, 600);
  }


  /* ========== Getters  ========== */
  public Client getClient() {
    return client;
  }

  public int getActiveGames() {
    return activeGames;
  }

  public ArrayList<String> getPlayerNames() {
    return playerNames;
  }
  
  public ArrayList<Integer> getPlayerNumbers() {
    return playerNumbers;
  }

  public String getCurrentName(int room) {
    return playerNames.get(room);
  }

  public int getCurrentNumPlayers(int room) {
    return playerNumbers.get(room);
  }
    
  /* ========== Setters ========== */
  public void setLoginScene() {
    window.setScene(loginScene());
  }

  public void setStartScene() {
    window.setScene(startScene());
  }
  
  public void setNumPlayersScene() throws IOException {
    window.setScene(numPlayersScene());
  }

  public void setGameScene(int room)  {
//    window.setScene(gameScene(room));
    window.setScene(game(room));
  }

  public void setWatchScene(int room) {
    window.setScene(watchScene(room));
  }

  public void setWinScene() {
    window.setScene(winScene());
  }

  public void setLoseScene(int room) {
    window.setScene(loseScene(room));
  }

  /* ========== Adders  ========== */
  public void addActiveGame() {
    activeGames++;
  }

  public void addPlayerName(String name) {
    playerNames.add(name);
  }

  public void addPlayerNumber(int num) {
    playerNumbers.add(num);
  }
  
  /* ========== Send and receive ========== */
  public void sendStr(String str) {
    try {
      client.send(str);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String receiveStr() {
    String ans = new String();
    try {
      ans = (String) client.receive();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return ans;
  }

  // send string with room
  public void sendStr(int room, String str) {
    try {
      client.sendViaChannel(room, str);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
  }
  
  // receive string with room
  public String receiveStr(int room) {
    String ans = new String();
    try {
      ans = (String) client.receiveViaChannel(room);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
    return ans;
  }

  // send with room
  public void sendObj(int room, Object obj) {
    try {
      client.sendViaChannel(room, obj);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
  }

  // receive obj with room
  public Object receiveObj(int room) {
    Object ans = new Object();
    try {
      ans = client.receiveViaChannel(room);
    } catch(Exception ex) {
        ex.printStackTrace();
    }
    return ans;
  }

  public Scene game(int currentRoom) {
    Group map = null;
    int currentNumPlayers = getCurrentNumPlayers(currentRoom - 1);
    try {
      System.out.println(String.format("/fxml/%dPlayerMap.fxml", currentNumPlayers));
      map = FXMLLoader.load(getClass().getResource(String.format("/fxml/%dPlayerMap.fxml", currentNumPlayers)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    URL resource = getClass().getResource("/fxml/game.fxml");
    FXMLLoader gameLoader = new FXMLLoader();
    gameLoader.setLocation(resource);
    gameLoader.setControllerFactory(c -> new GameController(this));
    BorderPane load = null;
    try {
      load = gameLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    GameController controller = gameLoader.getController();
    controller.setCurrentRoom(currentRoom).addBoard(client.getBoard(currentRoom - 1)).addMap(map);
    return new Scene(load);
  }
  
}
