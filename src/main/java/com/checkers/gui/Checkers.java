package com.checkers.gui;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.move.Position;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.*;
import com.checkers.models.prefs.Config;
import com.checkers.models.prefs.GameType;
import com.checkers.models.prefs.Level;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Class main checkers game UI and behavior controller
 */
public class Checkers {
    private final int WIDTH = Board.BOARD_SIZE * BoardSquare.SIZE;
    private final int HEIGHT = Board.BOARD_SIZE * BoardSquare.SIZE;
    private Board board;
    private BoardSquare[][] boardSquares;
    private Position origin;
    private boolean myTurn = true;
    Player player;
    public Config config;
    public Stage mainStage;
    public boolean highlightOnClick = true;
    public IntegerProperty elapsedTime = new SimpleIntegerProperty(0);
    public Background boxBackground = new Background(new BackgroundFill(Color.valueOf("#9E4B1A"), CornerRadii.EMPTY, Insets.EMPTY));
    public DropShadow dropShadow;
    private Insets padding = new Insets(10.0), margin = new Insets(10.0);
    private Font textFont = Font.font(null, FontWeight.BOLD, 20);
    private Color fillColor = Color.WHITE;

    public Checkers() {
        this.dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(2.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.valueOf("#6F2308"));
    }

    public static Checkers getInstance() {
        return new Checkers();
    }

    public void fadeInTransition(Parent root) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setNode(root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * Method start initializes ui elements and specifies behaviour of the ui components
     *
     * @param stage: Window stage
     * @throws Exception: Thread or Network connection exception
     */
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;
        config = (Config) (stage.getUserData());
        switch (config.getGameType()) {
            case COMPUTER -> this.player = getAgentByDifficulty(config.getLevel());
            case REMOTE -> {
                this.player = config.getPlayer();
                ((RemotePlayer) this.player).setOnMakeMoveCallbackFunc(this::remoteMove);

            }
        }
        if (player instanceof RemotePlayer) {
            this.myTurn = this.player.myTurn == Piece.PieceOwner.PLAYER1;
        }
        this.board = new Board();
        this.boardSquares = new BoardSquare[Board.BOARD_SIZE][Board.BOARD_SIZE];

        VBox outerLayout = initScoreBoard();

        GridPane gridPane = new GridPane();
        gridPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#FFF0DC"), CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPrefSize(WIDTH, HEIGHT);
        gridPane.setEffect(dropShadow);
        GridPane.setMargin(gridPane, margin);
        gridPane.setStyle("-fx-border-color: #e3d9d9; -fx-border-style: solid; -fx-border-width: 5px");
        this.initBoardSquares();
        for (int i = 0; i < this.boardSquares.length; i++) {
            for (int j = 0; j < this.boardSquares.length; j++) {
                gridPane.add(this.boardSquares[i][j], j, i);
            }
        }

        outerLayout.getChildren().addAll(gridPane, setLowerActionButtons());
        outerLayout.setAlignment(Pos.CENTER);
        outerLayout.setPadding(new Insets(10.0, 50.0, 10.0, 50.0));
        outerLayout.setOpacity(0.0);
        Platform.runLater(() -> {
            Scene scene = new Scene(outerLayout);
            stage.setTitle("Checkers");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
            fadeInTransition(outerLayout);
        });
    }

    /**
     * Method initScoreBoard initializes upper UI components that shows player names, scores and actions
     *
     * @return a JavaFX parent Node
     */
    private VBox initScoreBoard() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setMaxHeight(Double.NEGATIVE_INFINITY);
        vbox.setMaxWidth(Double.NEGATIVE_INFINITY);
        vbox.setMinHeight(Double.NEGATIVE_INFINITY);
        vbox.setStyle("-fx-background-color: #A14B1B");
        HBox newHBox1 = new HBox();
        HBox newHBox2 = new HBox();
        HBox newHBox3 = new HBox();
        newHBox1.setSpacing(30);
        newHBox2.setSpacing(15.0);
        Label player1Label = new Label(player != null ? player.getName() : "Player 1");
        player1Label.setFont(textFont);
        player1Label.setTextFill(fillColor);
        Label player1Score = new Label("0");
        player1Score.setFont(textFont);
        player1Score.setTextFill(fillColor);
        this.board.player1Score.addListener((observableValue, number, t1) -> {
            Platform.runLater(() -> {
                player1Score.setText(observableValue.getValue().toString());
            });
        });

        newHBox2.getChildren().addAll(player1Label, player1Score);
        newHBox2.setAlignment(Pos.CENTER_RIGHT);

        newHBox3.setSpacing(15.0);
        newHBox3.setAlignment(Pos.CENTER_LEFT);
        Label player2Label = new Label(player != null ? player.getOpponentName() : "Player 2");
        player2Label.setFont(textFont);
        player2Label.setTextFill(fillColor);
        Label player2Score = new Label("0");
        player2Score.setFont(textFont);
        player2Score.setTextFill(fillColor);
        this.board.player2Score.addListener((observableValue, number, t1) -> {
            Platform.runLater(() -> {
                player2Score.setText(observableValue.getValue().toString());
            });

        });
        newHBox3.getChildren().addAll(player2Score, player2Label);
        newHBox1.getChildren().addAll(newHBox2, newHBox3);
        newHBox1.setAlignment(Pos.CENTER);

        newHBox1.setBackground(boxBackground);
        newHBox1.setPadding(new Insets(5.0));
        newHBox1.setEffect(dropShadow);
        HBox.setMargin(newHBox1, margin);
        vbox.getChildren().addAll(newHBox1);
        vbox.setSpacing(10.0);
        return vbox;
    }

    /**
     * Class ScheduledTimer represents a timer task that periodically updates the elapsed time every second
     */
    private class ScheduledTimer extends TimerTask {

        @Override
        public void run() {
            elapsedTime.setValue(elapsedTime.getValue() + 1);
        }
    }

    /**
     * Method setLowerActionButtons sets buttom UI components hint, and home
     *
     * @return a JavaFx HBox Node
     */
    private HBox setLowerActionButtons() {
        HBox hBox4 = new HBox();
        hBox4.setBackground(boxBackground);
        hBox4.setPadding(new Insets(5.0));
        hBox4.setAlignment(Pos.CENTER);
        hBox4.setSpacing(25.0);
        ImageView imageView4 = new ImageView();
        imageView4.setImage(new Image("/idea.png"));
        imageView4.setFitWidth(25);
        imageView4.setFitHeight(25);
        Button hintButton = new Button();
        hintButton.setGraphic(imageView4);
        hintButton.setBackground(Background.EMPTY);
        hintButton.setPadding(Insets.EMPTY);
        hintButton.setTooltip(new Tooltip(highlightOnClick ? "Turn off highlighting" : "Turn on highlighting"));
        hintButton.setOnAction((e) -> {
            this.highlightOnClick = !this.highlightOnClick;
            updateBoard();
        });

        ImageView imageView1 = new ImageView();
        imageView1.setImage(new Image("/home.png"));
        imageView1.setFitWidth(25);
        imageView1.setFitHeight(25);
        Button homeButton = new Button();
        homeButton.setGraphic(imageView1);
        homeButton.setBackground(Background.EMPTY);
        homeButton.setPadding(Insets.EMPTY);
        homeButton.setTooltip(new Tooltip("Go to home menu"));
        homeButton.setOnAction((e) -> {
            gotoHome();
        });


        ImageView imageView = new ImageView();
        imageView.setImage(new Image("/undo.png"));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        Button undoButton = new Button();
        undoButton.setGraphic(imageView);
        undoButton.setBackground(Background.EMPTY);
        undoButton.setPadding(Insets.EMPTY);
        undoButton.setTooltip(new Tooltip("Undo last move"));
        undoButton.setOnAction((e) -> {
            board.undo();
            updateBoard();
            myTurn = !myTurn;
            if (!myTurn) {
                aiMove();
            }

        });


        ImageView imageView2 = new ImageView();
        imageView2.setFitHeight(25.0);
        imageView2.setFitWidth(25.0);
        imageView2.setImage(new Image("/refresh.png"));
        Button refreshButton = new Button();
        refreshButton.setGraphic(imageView2);
        refreshButton.setPadding(Insets.EMPTY);
        imageView.setFitHeight(25.0);
        imageView.setFitWidth(25.0);
        refreshButton.setBackground(Background.EMPTY);
        refreshButton.setOnMouseClicked((e) -> {
            board.resetBoard();
            updateBoard();
            board.player1Score.set(0);
            board.player2Score.set(0);
            myTurn = true;
            if (player instanceof RemotePlayer) myTurn = player.myTurn == Piece.PieceOwner.PLAYER1;
        });


        ImageView imageView3 = new ImageView();
        imageView3.setFitHeight(25.0);
        imageView3.setFitWidth(25.0);
        Button redoButton = new Button();
        redoButton.setGraphic(imageView3);
        redoButton.setPadding(Insets.EMPTY);
        redoButton.setBackground(Background.EMPTY);
        imageView3.setImage(new Image("/redo.png"));
        redoButton.setOnMouseClicked((e) -> {
            board.redo();
            updateBoard();
        });


        hBox4.getChildren().addAll(undoButton, refreshButton, redoButton, hintButton, homeButton);
        hBox4.setAlignment(Pos.CENTER);
        hBox4.setEffect(dropShadow);
        HBox.setMargin(hBox4, margin);
        return hBox4;
    }

    /**
     * Method gotoHome is a helper method to go to the start of game,
     * to reconfigure game parameters
     */
    private void gotoHome() {
        mainStage.close();
        System.out.println(config.getChoosePlayerScene());
        FXMLLoader fxmlLoader = new FXMLLoader(com.checkers.gui.ChoosePlayer.class.getResource("/choose-player.fxml"));
        try {
            Scene nextScene = new Scene(fxmlLoader.load());
            mainStage.setScene(nextScene);
            mainStage.setTitle("Choose Player");
            mainStage.show();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method getAgentByDifficulty gives an AI agent based on selected difficulty level
     *
     * @param level: game difficulty level
     * @return an AI player
     * @throws Exception: signifies a level cannot be null
     */
    private Player getAgentByDifficulty(Level level) throws Exception {
        if (level == null) throw new Exception("Level cannot be null");
        Player player;
        switch (level) {
            case EASY -> player = new RandomPlayer("Random Player", Piece.PieceOwner.PLAYER2);
            case HARD -> player = new AlphaBetaMinMaxAIPlayer("Alpha Beta", Piece.PieceOwner.PLAYER2);
            case MEDIUM -> player = new MinMaxAIPlayer("MiniMaxAI", Piece.PieceOwner.PLAYER2);
            case EXPERT -> player = new BackRowAIPlayer("BackRowAI", Piece.PieceOwner.PLAYER2);
            default -> player = new IterativeDeepeningAIPlayer("John Doe", Piece.PieceOwner.PLAYER2);
        }

        return player;
    }

    /**
     * Method initBoardSquares initializes board squares and attachs event handlers
     */
    public void initBoardSquares() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                BoardSquare square = new BoardSquare(i, j);
                if (this.board.getPiece(i, j) == null) square.setPiece();
                else square.setPiece(this.board.getPiece(i, j).toString());

                square.setHighlight(false);
                square.setOnAction(actionEvent -> boardClicked(square.getCoordinate()));
                this.boardSquares[i][j] = square;
            }
        }
    }

    /**
     * Method updateBoard updates the pieces on a board based on moves taken a prior
     */
    public void updateBoard() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = (1 - i % 2); j < Board.BOARD_SIZE; j += 2) {
                if (this.board.getPiece(i, j) == null) this.boardSquares[i][j].setPiece();
                else this.boardSquares[i][j].setPiece(this.board.getPiece(i, j).toString());

            }
        }
    }

    /**
     * Method for handling user clicks on the game board
     *
     * @param clickedPos: the position where a mouse click was captured
     */
    public void boardClicked(Position clickedPos) {
        if (!myTurn) return;

        Piece p = board.getPiece(clickedPos);
        if (this.origin == null) {
            if (p == null) return;

            if (p.owner == board.getTurn()) {
                this.origin = clickedPos;
                if (this.highlightOnClick)
                    this.setHighlight(this.board.reachablePositions(clickedPos), this.highlightOnClick);
            }
        } else {
            if (p != null && p.owner == board.getTurn()) {
                this.origin = clickedPos;
                if (this.highlightOnClick) this.turnOffAllHighlights();
                this.setHighlight(this.board.reachablePositions(this.origin), this.highlightOnClick);
                return;
            }

            Move move = new Move(this.origin, clickedPos);
            try {
                this.board.makeMove(move);
                updateBoard();
                isThereWinner();
                if (config.getGameType() != GameType.PLAYER) this.myTurn = !this.myTurn;
                if (player instanceof AIPlayer || player instanceof RandomPlayer) { //AI will make a move
                    this.aiMove();
                }
                if (player instanceof RemotePlayer) {
                    player.makeMove(move);
                }


            } catch (InValidMove e) {
                System.out.println(e.getMessage());
            } finally {
                this.origin = null;
                this.turnOffAllHighlights();
            }
            // create a move and send it to board
            // inside board check for a move that has the same origin and destination, if so execute
        }
    }

    /**
     * A Callback method to update local board when a remote move has been made by our opponent
     *
     * @param move: move taken by our remote opponent
     * @return void
     */
    public Void remoteMove(Move move) {
        try {
            board.makeMove(move);
            updateBoard();
        } catch (InValidMove e) {
            throw new RuntimeException(e);
        }
        this.myTurn = !this.myTurn;
        return null;
    }

    /**
     * Helper method showDialog displays a dialog after a game is over.
     *
     * @param won: represents weather the player has won or lost
     */
    public void showDialog(boolean won) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Game Over");
        dialog.setContentText(won ? "You WON!" : "You Lost!");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(cancelButton);
        dialog.showAndWait();
    }

    /**
     * Method aiMove lets an AI agent make a move based on its algorithms and heuristics
     */
    public void aiMove() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                Move aiMove = player.makeMove(new Board(board));
                board.makeMove(aiMove);
                Platform.runLater(() -> updateBoard());
                return null;
            }

            @Override
            protected void done() {
                super.done();
                isThereWinner();
                myTurn = !myTurn;
            }
        };

        Thread newTask = new Thread(task);
        newTask.start();

    }

    /**
     * Method setHighlight makes squares on a board highlighted
     *
     * @param moves: positions to highlight
     * @param light: weather to turn on or off the moves
     */
    private void setHighlight(ArrayList<Move> moves, boolean light) {
        for (Move mv : moves) {
            this.boardSquares[mv.getDestination().getRow()][mv.getDestination().getColumn()].setHighlight(light);
        }
    }

    /**
     * Method turnOffAllHighlights turns of highlighted squares in a board
     */
    private void turnOffAllHighlights() {
        for (BoardSquare[] boardSquare : this.boardSquares) {
            for (BoardSquare bs : boardSquare) {
                bs.setHighlight(false);
            }
        }
    }

    /**
     * Method isThereWinner checks weather there is a winner in response to a move
     */
    public void isThereWinner() {
        Piece.PieceOwner winner = this.board.isGameOver();
        if (winner != null) {
            //   JOptionPane.showMessageDialog(this, winner + " won.", "WINNER ALERT " , JOptionPane.INFORMATION_MESSAGE);
            System.out.println(winner + " is the winner");
            Platform.runLater(() -> {
                if (player instanceof AIPlayer || player instanceof RandomPlayer) {
                    showDialog(winner == Piece.PieceOwner.PLAYER1);
                } else if (player instanceof RemotePlayer) {
                    showDialog(winner == player.myTurn);
                } else {
                    showDialog(winner == Piece.PieceOwner.PLAYER1);
                }
            });
        }
    }
}
