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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import static com.checkers.util.SceneUtils.fadeInTransition;
import static com.checkers.util.SceneUtils.fakeOutTransition;

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
        System.out.println("Initial state before join: " + connectionStatus.getConnectionStatus().toString());

        setStage(actionEvent);
        if (connectionStatus.getConnectionStatus() == ActionStatus.WAITING_FOR_OPPONENT || connectionStatus.getConnectionStatus() == ActionStatus.COMPLETED) {
            connectionStatus.setConnectionStatus(ActionStatus.FAILED);
            return;
        }
        String port, name, hostname;
        name = this.name.getText().trim();
        hostname = this.hostname.getText().trim();
        port = this.port.getText().trim();
        if (name.length() < 2 || hostname.length() < 4 || port.length() < 2) {
            connectionStatus.setConnectionStatus(ActionStatus.FAILED);
            return;
        }
        Runnable joinGameRunnable = () -> {
            Platform.runLater(() -> connectionStatus.setConnectionStatus(ActionStatus.JOINING_GAME));
            try {
                player = new RemotePlayer(name, Piece.PieceOwner.PLAYER2, hostname, Integer.parseInt(port));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        CompletableFuture<Void> joinGameFuture = CompletableFuture.runAsync(joinGameRunnable);

        joinGameFuture.thenRun(() -> {
            connectionStatus.setConnectionStatus(ActionStatus.COMPLETED);
        }).exceptionally((e) -> {
            connectionStatus.setConnectionStatus(ActionStatus.FAILED);
            joinGameFuture.cancel(true);
            return null;
        });
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
        System.out.println("Initial state: " + connectionStatus.getConnectionStatus().toString());
        setStage(actionEvent);
        if (connectionStatus.getConnectionStatus() == ActionStatus.COMPLETED || connectionStatus.getConnectionStatus() == ActionStatus.WAITING_FOR_OPPONENT)
            return;
        String nameValue, hostValue, portValue;
        nameValue = name.getText().trim();
        hostValue = hostname.getText().trim();
        portValue = port.getText().trim();
        final String name, host;
        final int porta;
        name = nameValue.length() < 2 ? "John Doe" : nameValue;
        host = hostValue.length() < 4 ? "127.0.0.1" : hostValue;
        porta = portValue.length() < 3 ? 6060 : Integer.parseInt(portValue);

        Runnable hostGameRunnable = () -> {
            connectionStatus.setConnectionStatus(ActionStatus.WAITING_FOR_OPPONENT);

            try {
                player = new RemotePlayer(name, Piece.PieceOwner.PLAYER1, host, porta);
            } catch (Exception e) {
                System.out.println("Exception in hosting raised");
                throw new RuntimeException(e);
            }
            System.out.println("Host game finished success");
        };

        CompletableFuture<Void> hostGameFuture = CompletableFuture.runAsync(hostGameRunnable);
        hostGameFuture.thenRun(() -> {
            connectionStatus.setConnectionStatus(ActionStatus.COMPLETED);
        }).exceptionally((e) -> {
            e.printStackTrace();
            connectionStatus.setConnectionStatus(ActionStatus.FAILED);
            return null;
        });
    }

    /**
     * Method showScene sets the scene of the stage to the checkers game and launches it
     *
     * @throws CouldntConnectToServerException: exception in connecting to remote host or hosting on specific port
     */
    private void showScene() throws CouldntConnectToServerException {
        ((Config) stage.getUserData()).setPlayer(player);
        fakeOutTransition(currentScene.getRoot(), (arg) -> {
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
        public synchronized void setConnectionStatus(ActionStatus connectionStatus) {
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
        Platform.runLater(() -> {
            overlayPane.setVisible(true);
            statusLabel.setText("Joining Game");
        });
    }

    /**
     * Method handles status change to Waiting for opponent state
     *
     * @param previousStatus: previous state of the connection
     */
    private void handleWaitingForOpponent(ActionStatus previousStatus) {
        Platform.runLater(() -> {
            overlayPane.setVisible(true);
            statusLabel.setText("Waiting Opponent");
        });

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
        Platform.runLater(() -> {
            statusLabel.setText(ActionStatus.FAILED.name());
            overlayPane.setVisible(false);
            errorLabel.setVisible(true);
        });
    }
}
