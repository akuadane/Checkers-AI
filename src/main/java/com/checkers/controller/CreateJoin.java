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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CreateJoin {
    @FXML
    TextField name;
    @FXML
    TextField gameID;
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
        if (connectionStatus.getConnectionStatus() == ActionStatus.CONNECTING_TO_SERVER || connectionStatus.getConnectionStatus() == ActionStatus.WAITING_FOR_OPPONENT)
            return;
        String name, gameID;
        name = this.name.getText().trim();
        gameID = this.gameID.getText().trim();
        try {
            Runnable connectToServerRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.CONNECTING_TO_SERVER));
                try {
                    player = new RemotePlayer(name, Piece.PieceOwner.PLAYER2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            Runnable joinGameRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.JOINING_GAME));
                try {
                    player.joinGame(gameID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            Runnable waitOpponentRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.WAITING_FOR_OPPONENT));

                try {
                    player.waitForOpponent();
                    System.out.println("Waiting opponent is over");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            CompletableFuture<Void> connectToServerFuture = CompletableFuture.runAsync(connectToServerRunnable);
            CompletableFuture<Void> createGameFuture = connectToServerFuture.thenRunAsync(joinGameRunnable);
            CompletableFuture<Void> waitOpponentFuture = createGameFuture.thenRunAsync(waitOpponentRunnable);
            CompletableFuture<Void> finalFuture = waitOpponentFuture.thenRunAsync(() -> connectionStatus.setConnectionStatus(ActionStatus.COMPLETED));
            finalFuture.thenRun(() -> {
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
        if (connectionStatus.getConnectionStatus() == ActionStatus.CONNECTING_TO_SERVER || connectionStatus.getConnectionStatus() == ActionStatus.WAITING_FOR_OPPONENT)
            return;
        String name, gameID;
        name = this.name.getText().trim();
        gameID = this.gameID.getText().trim();
        try {

            Runnable connectToServerRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.CONNECTING_TO_SERVER));
                try {
                    player = new RemotePlayer(name, Piece.PieceOwner.PLAYER2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            Runnable createGameRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.CREATING_GAME));
                try {
                    player.createGame(gameID);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };
            Runnable waitOpponentRunnable = () -> {
                Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.WAITING_FOR_OPPONENT));
                try {
                    player.waitForOpponent();
                    System.out.println("Waiting opponent is over");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            };

            CompletableFuture<Void> connectToServerFuture = CompletableFuture.runAsync(connectToServerRunnable);
            CompletableFuture<Void> createGameFuture = connectToServerFuture.thenRunAsync(createGameRunnable);
            CompletableFuture<Void> waitOpponentFuture = createGameFuture.thenRunAsync(waitOpponentRunnable);
            CompletableFuture<Void> finalFuture = waitOpponentFuture.thenRunAsync(() -> connectionStatus.setConnectionStatus(ActionStatus.COMPLETED));
            finalFuture.thenRunAsync(() -> {
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
                case CONNECTING_TO_SERVER -> handleConnectingToServer(previousState);
                case JOINING_GAME -> handleJoiningGame(previousState);
                case CREATING_GAME -> handleCreatingGame(previousState);
                case WAITING_FOR_OPPONENT -> handleWaitingForOpponent(previousState);
                case COMPLETED -> handleCompleted(previousState);
            }
        }
    }

    private void handleCreatingGame(ActionStatus previousStatus) {
        overlayPane.setVisible(true);
        statusLabel.setText(ActionStatus.CREATING_GAME.name());
    }

    private void handleJoiningGame(ActionStatus previousStatus) {
        overlayPane.setVisible(true);
        statusLabel.setText(ActionStatus.JOINING_GAME.name());
    }

    private void handleWaitingForOpponent(ActionStatus previousStatus) {
        overlayPane.setVisible(true);
        statusLabel.setText(ActionStatus.WAITING_FOR_OPPONENT.name());

    }

    private void handleConnectingToServer(ActionStatus previousStatus) {
        overlayPane.setVisible(true);
        statusLabel.setText(ActionStatus.CONNECTING_TO_SERVER.name());

    }

    private void handleCompleted(ActionStatus previousStatus) {
        overlayPane.setVisible(false);
        statusLabel.setText(ActionStatus.COMPLETED.name());
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
