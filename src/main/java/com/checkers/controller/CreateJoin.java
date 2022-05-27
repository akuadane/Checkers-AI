package com.checkers.controller;

import com.checkers.gui.Checkers;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.RemotePlayer;
import com.checkers.models.prefs.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateJoin {
    @FXML
    TextField name;
    @FXML
    TextField gameID;
    @FXML
    Button joinButton;
    @FXML
    Button createButton;


    public void onJoinAction(ActionEvent actionEvent) {
        String name, gameID;
        name = this.name.getText().trim();
        gameID = this.gameID.getText().trim();
        try {
            RemotePlayer player = new RemotePlayer(name, Piece.PieceOwner.PLAYER2);
            player.connectToServer();
            player.joinGame(gameID);

            //
            Scene currentScene = ((Node) actionEvent.getSource()).getScene();
            Stage stage = (Stage) (currentScene.getWindow());
            ((Config) stage.getUserData()).setPlayer(player);
            Checkers game = Checkers.getInstance();
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCreateAction(ActionEvent actionEvent) {
        String name, gameID;
        name = this.name.getText().trim();
        gameID = this.gameID.getText().trim();
        try {
            RemotePlayer player = new RemotePlayer(name, Piece.PieceOwner.PLAYER2);
            player.connectToServer();
            player.createGame(gameID);

            Scene currentScene = ((Node) actionEvent.getSource()).getScene();
            Stage stage = (Stage) (currentScene.getWindow());
            ((Config) stage.getUserData()).setPlayer(player);
            Checkers game = Checkers.getInstance();
            game.start(stage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
