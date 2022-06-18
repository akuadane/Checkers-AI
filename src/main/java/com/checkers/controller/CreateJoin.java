package com.checkers.controller;

import com.checkers.gui.Checkers;
import com.checkers.models.ActionStatus;
import com.checkers.models.exceptions.CouldntConnectToServerException;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.RemotePlayer;
import com.checkers.models.prefs.Config;
import com.checkers.util.SceneUtils;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * Class CreateJoin represents a controller class for choosing and establishing a remote game
 */
public class CreateJoin implements Initializable {
    @FXML
    AnchorPane anchorPane;
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
    private Scene currentScene;

    /**
     * Event Handler method for joining a remote game hosted by another player
     *
     * @param actionEvent: represents a mouse event selection of Join Game
     */
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

    /**
     * Helper method for setting the stage of the GUI used to attach scenes on it
     *
     * @param actionEvent
     */
    private void setStage(ActionEvent actionEvent) {
        currentScene = ((Node) actionEvent.getSource()).getScene();
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }

    /**
     * Method onCreateAction represents an Event Handler method for creating a Remote Game.
     *
     * @param actionEvent: represents a mouse event on Create Action selection
     */
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

    /**
     * Method showScene sets the scene of the stage to the checkers game and launches it
     *
     * @throws CouldntConnectToServerException: exception in connecting to remote host or hosting on specific port
     */
    private void showScene() throws CouldntConnectToServerException {
        ((Config) stage.getUserData()).setPlayer(player);
        fadeOutTransition(currentScene.getRoot(), (arg) -> {
            Checkers game = Checkers.getInstance();
            try {
                game.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane.setOpacity(0.0);
        fadeInTransition(anchorPane);
    }

    private void fadeInTransition(AnchorPane anchorPane) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(SceneUtils.ANIMATION_DURATION));
        fadeTransition.setNode(anchorPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void fadeOutTransition(Parent parent, Callback<Void, Void> function) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(SceneUtils.ANIMATION_DURATION));
        fadeTransition.setNode(parent);
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

    /**
     * Class ConnectionProgress represents the connection status of remote game
     * and the transition between states.
     */
    private class ConnectionProgress {
        private ActionStatus connectionStatus = ActionStatus.NOT_STARTED;

        public ActionStatus getConnectionStatus() {
            return connectionStatus;
        }

        /**
         * Method setConnectionStatus updates the connection status
         *
         * @param connectionStatus: new connection status
         */
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

    /**
     * Method handles status change to Hosting game state
     *
     * @param previousState
     */
    private void handleHostingGame(ActionStatus previousState) {

    }

    /**
     * Method handles status change to Joining game state
     *
     * @param previousStatus: previous state of the connection
     */
    private void handleJoiningGame(ActionStatus previousStatus) {
        overlayPane.setVisible(true);
        statusLabel.setText(ActionStatus.JOINING_GAME.name());
    }

    /**
     * Method handles status change to Waiting for opponent state
     *
     * @param previousStatus: previous state of the connection
     */
    private void handleWaitingForOpponent(ActionStatus previousStatus) {
        overlayPane.setVisible(true);
        statusLabel.setText(ActionStatus.WAITING_FOR_OPPONENT.name());

    }

    /**
     * Method handles status change to connection establishment Completed state
     *
     * @param previousStatus: previous connection state
     */
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

    /**
     * Method handles status change to failure in establishing remote game connection
     *
     * @param previousStatus: previous state of the connection
     */
    private void handleOnFailed(ActionStatus previousStatus) {
        statusLabel.setText(ActionStatus.FAILED.name());
        overlayPane.setVisible(false);
        errorLabel.setVisible(true);
    }
}
