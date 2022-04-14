package com.company.main.controller;

import com.company.main.models.GameType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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

    private GameType gameType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /*
     * Handler method for play with computer choice
     * */
    public void playWithComputer(ActionEvent actionEvent) {
        gameType = GameType.COMPUTER;
//        System.out.println("Game type changed to: " + gameType);
    }

    /*
     * Handler method for play remote choice
     * */
    public void playRemote(ActionEvent actionEvent) {
        gameType = GameType.PLAYER;
//        System.out.println("Game type changed to: " + gameType);
    }

    /*
     * Handler method for play with player choice
     * */
    public void playWithPlayer(ActionEvent actionEvent) {
        gameType = GameType.REMOTE;
//        System.out.println("Game type changed to: " + gameType);
    }
}
