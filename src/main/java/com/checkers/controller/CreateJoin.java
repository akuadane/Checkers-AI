package com.checkers.controller;

import com.checkers.gui.Checkers;
import com.checkers.models.ActionStatus;
import com.checkers.models.exceptions.CouldntConnectToServerException;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.RemotePlayer;
import com.checkers.models.prefs.Config;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;

public class CreateJoin {
    @FXML
    TextField name;
    @FXML
    TextField hostname;
    @FXML
    TextField port;
    @FXML
    Button joinButton;
    @FXML
    Button createButton;
    @FXML
    AnchorPane overlayPane;
    @FXML
    ProgressIndicator progressIndicator;
    @FXML
    Label statusLabel;
    @FXML
    Label errorLabel;
    RemotePlayer player;
    ConnectionProgress connectionStatus = new ConnectionProgress();
    Stage stage;

    public void onJoinAction(ActionEvent actionEvent) {
        setStage(actionEvent);
        if (connectionStatus.getConnectionStatus() == ActionStatus.WAITING_FOR_OPPONENT || connectionStatus.getConnectionStatus() == ActionStatus.COMPLETED)
            return;
        String port, name, hostname;
        name = this.name.getText().trim();
        hostname = this.hostname.getText().trim();
        port = this.port.getText().trim();
        try {
            Runnable joinGameRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.JOINING_GAME));
                try {
                    player = new RemotePlayer(name, Piece.PieceOwner.PLAYER2, hostname, Integer.parseInt(port));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            Runnable waitOpponentRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.WAITING_FOR_OPPONENT));
                try {
                    Thread.sleep((long) Double.POSITIVE_INFINITY);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            CompletableFuture<Void> joinGameFuture = CompletableFuture.runAsync(joinGameRunnable);
            CompletableFuture<Void> waitOpponentFuture = CompletableFuture.runAsync(waitOpponentRunnable);

            joinGameFuture.thenRun(() -> player.setWaitOpponentFuture(waitOpponentFuture));

            waitOpponentFuture.thenRun(() -> {
                connectionStatus.setConnectionStatus(ActionStatus.COMPLETED);
            });


        } catch (Exception e) {
            connectionStatus.setConnectionStatus(ActionStatus.FAILED);
            e.printStackTrace();
        }
    }

    private void setStage(ActionEvent actionEvent) {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    public void onCreateAction(ActionEvent actionEvent) {
        setStage(actionEvent);
        if (connectionStatus.getConnectionStatus() == ActionStatus.COMPLETED || connectionStatus.getConnectionStatus() == ActionStatus.WAITING_FOR_OPPONENT)
            return;
        String name, gameID;
        name = this.name.getText().trim();
        gameID = this.hostname.getText().trim();
        try {

            Runnable hostGameRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.HOSTING_GAME));
                try {
                    player = new RemotePlayer(name, Piece.PieceOwner.PLAYER1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            Runnable waitOpponentRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.WAITING_FOR_OPPONENT));
                try {
                    Thread.sleep((long) Double.POSITIVE_INFINITY);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            CompletableFuture<Void> waitOpponentFuture = CompletableFuture.runAsync(waitOpponentRunnable);
            CompletableFuture<Void> hostGameFuture = CompletableFuture.runAsync(hostGameRunnable);
            hostGameFuture.thenRun(() -> player.setWaitOpponentFuture(waitOpponentFuture));

            waitOpponentFuture.thenRun(() -> {
                connectionStatus.setConnectionStatus(ActionStatus.COMPLETED);
            });
        } catch (Exception e) {
            Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.FAILED));
            e.printStackTrace();
        }
    }

    private void showScene() throws CouldntConnectToServerException {
        ((Config) stage.getUserData()).setPlayer(player);
        Checkers game = Checkers.getInstance();
        try {
            game.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ConnectionProgress {
        private ActionStatus connectionStatus = ActionStatus.NOT_STARTED;

        public ActionStatus getConnectionStatus() {
            return connectionStatus;
        }

        public void setConnectionStatus(ActionStatus connectionStatus) {
            ActionStatus previousState = this.connectionStatus;
            this.connectionStatus = connectionStatus;
            switch (this.connectionStatus) {
                case FAILED -> handleOnFailed(previousState);
                case JOINING_GAME -> handleJoiningGame(previousState);
                case HOSTING_GAME -> handleHostingGame(previousState);
                case WAITING_FOR_OPPONENT -> handleWaitingForOpponent(previousState);
                case COMPLETED -> handleCompleted(previousState);
            }
        }
    }

    private void handleHostingGame(ActionStatus previousState) {

    }

    private void handleJoiningGame(ActionStatus previousStatus) {
        overlayPane.setVisible(true);
        statusLabel.setText(ActionStatus.JOINING_GAME.name());
    }

    private void handleWaitingForOpponent(ActionStatus previousStatus) {
        overlayPane.setVisible(true);
        statusLabel.setText(ActionStatus.WAITING_FOR_OPPONENT.name());

    }

    private void handleCompleted(ActionStatus previousStatus) {
        Platform.runLater(() -> {
            overlayPane.setVisible(false);
            statusLabel.setText(ActionStatus.COMPLETED.name());
        });

        try {
            showScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleOnFailed(ActionStatus previousStatus) {
        statusLabel.setText(ActionStatus.FAILED.name());
        overlayPane.setVisible(false);
        errorLabel.setVisible(true);
    }
}
