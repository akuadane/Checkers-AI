package com.checkers.controller;


import com.checkers.gui.Checkers;
import com.checkers.models.exceptions.CouldntConnectToServerException;
import com.checkers.models.prefs.Config;
import com.checkers.models.prefs.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for choosing game level
 */
public class ChooseLevel {
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
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
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
    }
}
