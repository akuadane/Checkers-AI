package com.checkers.controller;

import com.checkers.gui.Checkers;
import com.checkers.models.exceptions.CouldntConnectToServerException;
import com.checkers.models.prefs.Config;
import com.checkers.models.prefs.GameType;
import com.checkers.util.SceneUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/*
 * Controller class for choosing opponent player.
 * */
public class ChoosePlayer implements Initializable {
    @FXML
    public Button playWithComputerButton;
    @FXML
    public Button playWithPlayerButton;
    @FXML
    public Button playRemoteButton;

    private GameType gameType = GameType.COMPUTER;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /*
     * Handler method for play with computer choice
     * */
    public void playWithComputer(ActionEvent actionEvent) throws IOException {
        this.gameType = GameType.COMPUTER;
        FXMLLoader fxmlLoader = new FXMLLoader(com.checkers.controller.ChoosePlayer.class.getResource("/choose-level.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Scene currentScene = ((Node) actionEvent.getSource()).getScene();
        Stage stage = (Stage) currentScene.getWindow();
        stage.setTitle("Checkers AI");
        stage.centerOnScreen();
        ((Config) stage.getUserData()).setGameType(gameType);
        SceneUtils.switchScene(currentScene, scene);
    }

    /*
     * Handler method for play remote choice
     * */
    public void playRemote(ActionEvent actionEvent) throws IOException {
        gameType = GameType.REMOTE;
        FXMLLoader fxmlLoader = new FXMLLoader(com.checkers.gui.ChoosePlayer.class.getResource("/remote-setup.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Scene currentScene = ((Node) actionEvent.getSource()).getScene();
        Stage stage = (Stage) currentScene.getWindow();
        stage.setTitle("Checkers AI");
        stage.centerOnScreen();
        ((Config) stage.getUserData()).setGameType(gameType);
        SceneUtils.switchScene(currentScene, scene);
    }

    /*
     * Handler method for play with player choice
     * */
    public void playWithPlayer(ActionEvent actionEvent) {
        gameType = GameType.PLAYER;
        startGame(actionEvent);
    }

    public void startGame(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Checkers game = Checkers.getInstance();
        stage.setTitle("Checkers AI");
        stage.centerOnScreen();
        ((Config) stage.getUserData()).setGameType(gameType);
        System.out.println(stage.getUserData());
        try {
            game.start(stage);
        } catch (CouldntConnectToServerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
