package com.company.main.controller;

import com.company.main.models.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    /*
     * Called on easy button action
     * */
    public void easy(ActionEvent actionEvent) {
        difficulty = Level.EASY;
    }

    /*
     * Called on medium button action
     * */
    public void medium(ActionEvent actionEvent) {
        difficulty = Level.MEDIUM;
    }

    /*
     * Called on hard button action
     * */
    public void hard(ActionEvent actionEvent) {
        difficulty = Level.HARD;
    }

    /*
     * Called on expert button action
     * */
    public void expert(ActionEvent actionEvent) {
        difficulty = Level.EXPERT;
    }
}
