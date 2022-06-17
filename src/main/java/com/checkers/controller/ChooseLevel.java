package com.checkers.controller;


import com.checkers.gui.Checkers;
import com.checkers.models.exceptions.CouldntConnectToServerException;
import com.checkers.models.prefs.Config;
import com.checkers.models.prefs.Level;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for choosing game level
 */
public class ChooseLevel implements Initializable {
    @FXML
    AnchorPane anchorPane;
    @FXML
    public Button easyButton;
    @FXML
    public Button hardButton;
    @FXML
    public Button mediumButton;
    @FXML
    public Button expertButton;

    private Level difficulty;

    /**
     * Called on easy button action
     */
    public void easy(ActionEvent actionEvent) {
        difficulty = Level.EASY;
        startGame(actionEvent);
    }

    /**
     * Called on medium button action
     */
    public void medium(ActionEvent actionEvent) {
        difficulty = Level.MEDIUM;
        startGame(actionEvent);
    }

    /**
     * Called on hard button action
     */
    public void hard(ActionEvent actionEvent) {
        difficulty = Level.HARD;
        startGame(actionEvent);
    }

    /**
     * Called on expert button action
     */
    public void expert(ActionEvent actionEvent) {
        difficulty = Level.EXPERT;
        startGame(actionEvent);
    }

    /**
     * Method startGame starts the checkers game
     */
    public void startGame(ActionEvent actionEvent) {
        Scene currentScene = ((Node) actionEvent.getSource()).getScene();
        fadeOutTransition(currentScene.getRoot(), (arg) -> {
            Stage stage = (Stage) currentScene.getWindow();
            Checkers game = Checkers.getInstance();
            stage.setTitle("Choose Level");
            stage.centerOnScreen();
            ((Config) stage.getUserData()).setLevel(difficulty);
            try {
                game.start(stage);
            } catch (CouldntConnectToServerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
        });

    }

    private void fadeOutTransition(Parent root, Callback<Void, Void> function) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(actionEvent -> {
            try {
                function.call(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fadeTransition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.setOpacity(0.0);
        fakeInTransition(anchorPane);
    }

    private void fakeInTransition(AnchorPane parent) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(parent);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
