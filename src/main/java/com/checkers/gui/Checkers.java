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
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
    public IntegerProperty player1Score = new SimpleIntegerProperty(0);
    public IntegerProperty player2Score = new SimpleIntegerProperty(0);
    public IntegerProperty elapsedTime = new SimpleIntegerProperty(0);
    public Date startTime;

    public static Checkers getInstance() {
        return new Checkers();
    }

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

        gridPane.setPrefSize(WIDTH, HEIGHT);
        this.initBoardSquares();
        for (int i = 0; i < this.boardSquares.length; i++) {
            for (int j = 0; j < this.boardSquares.length; j++) {
                gridPane.add(this.boardSquares[i][j], j, i);
            }
        }

        outerLayout.getChildren().addAll(gridPane, setLowerActionButtons());
        Platform.runLater(() -> {
            Scene scene = new Scene(outerLayout, WIDTH, HEIGHT + 200);
            stage.setTitle("Checkers");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
        Timer timer = new Timer();
        ScheduledTimer st = new ScheduledTimer();
        timer.schedule(st, 0, 1000);
        startTime = new Date();
    }

    private VBox initScoreBoard() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setMaxHeight(Double.NEGATIVE_INFINITY);
        vbox.setMaxWidth(Double.NEGATIVE_INFINITY);
        vbox.setMinHeight(Double.NEGATIVE_INFINITY);
//        vbox.setPrefSize(768.0, 500.0);
        vbox.setStyle("-fx-background-color: #ffccffff");
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(200.0);
//        VBox hBox = new VBox();
        HBox newHBox1 = new HBox();
        HBox newHBox2 = new HBox();
        HBox newHBox3 = new HBox();
        newHBox1.setSpacing(30);
//        HBox hBox1 = new HBox();
        newHBox2.setSpacing(20.0);
        Label player1Label = new Label(player != null ? player.getName() : "Player 1");
        player1Label.setFont(new Font(16.0));
        Label player1Score = new Label("0");
        this.player1Score.addListener((observableValue, number, t1) -> {
            player1Score.setText(observableValue.getValue().toString());
        });
        player1Score.setFont(new Font(16.0));
        newHBox2.getChildren().addAll(player1Label, player1Score);
        newHBox2.setAlignment(Pos.CENTER_RIGHT);

//        HBox hBox2 = new HBox();
        newHBox3.setSpacing(20.0);
        newHBox3.setAlignment(Pos.CENTER_LEFT);
        Label player2Label = new Label(player != null ? player.getOpponentName() : "Player 2");
        player2Label.setFont(new Font(16.0));
        Label player2Score = new Label("0");
        this.player2Score.addListener((observableValue, number, t1) -> {
            player2Score.setText(observableValue.getValue().toString());
        });
        player2Score.setFont(new Font(16.0));
        newHBox3.getChildren().addAll(player2Score, player2Label);
        newHBox1.getChildren().addAll(newHBox2, newHBox3);
        newHBox1.setAlignment(Pos.CENTER);


        HBox hBox3 = new HBox();
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setSpacing(20.0);
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("/undo.png"));
        imageView.setOnMouseClicked((e) -> {
            board.undo();
            updateBoard();
            myTurn = !myTurn;
            if (!myTurn) {
                aiMove();
            }

        });
        imageView.setFitHeight(25.0);
        imageView.setFitWidth(25.0);

        ImageView imageView2 = new ImageView();
        imageView2.setFitHeight(25.0);
        imageView2.setFitWidth(25.0);
        imageView2.setImage(new Image("/refresh.png"));
        imageView2.setOnMouseClicked((e) -> {
            updateBoard();
        });
        imageView.setFitHeight(25.0);
        imageView.setFitWidth(25.0);

        ImageView imageView3 = new ImageView();
        imageView3.setFitHeight(25.0);
        imageView3.setFitWidth(25.0);
        imageView3.setImage(new Image("/redo.png"));
        imageView3.setOnMouseClicked((e) -> {

        });

        hBox3.getChildren().addAll(imageView, imageView2, imageView3);
//        hBox.getChildren().addAll(vBox1, hBox2);
        Label timeLabel = new Label("00:00");
        elapsedTime.addListener((observableValue, number, t1) -> {
            int minutes = elapsedTime.getValue() / 60;
            int seconds = elapsedTime.getValue() - (minutes * 60);
            String min = String.format("%2d", minutes).replace(' ', '0');
            String sec = String.format("%2d", seconds).replace(' ', '0');
            Platform.runLater(() -> {
                timeLabel.setText(min + ":" + sec);
            });

        });
        timeLabel.setFont(new Font(16.0));
        hBox.getChildren().addAll(newHBox1, hBox3);
        vbox.getChildren().addAll(hBox, timeLabel);
        return vbox;
    }

    private class ScheduledTimer extends TimerTask {

        @Override
        public void run() {
            elapsedTime.setValue(elapsedTime.getValue() + 1);
        }
    }

    private HBox setLowerActionButtons() {
        HBox hBox4 = new HBox();
        hBox4.setAlignment(Pos.CENTER);
        hBox4.setSpacing(20.0);
        Button hintButton = new Button("HINT");
        hintButton.setOnAction((e) -> {
            this.highlightOnClick = !this.highlightOnClick;
            updateBoard();
        });
        ImageView imageView1 = new ImageView();
        imageView1.setImage(new Image("/home.png"));
        imageView1.setOnMouseClicked((e) -> {
            gotoHome();
        });
        imageView1.setFitHeight(25.0);
        imageView1.setFitWidth(25.0);
        hBox4.getChildren().addAll(hintButton, imageView1);
        hBox4.setAlignment(Pos.CENTER);
        return hBox4;
    }

    private void gotoHome() {
        mainStage.close();
        System.out.println(config.getChoosePlayerScene());
        mainStage.setScene(config.getChoosePlayerScene());
        mainStage.setTitle("Choose Player");
        mainStage.show();
        System.out.println("Done");
    }


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

    public void updateBoard() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = (1 - i % 2); j < Board.BOARD_SIZE; j += 2) {
                if (this.board.getPiece(i, j) == null) this.boardSquares[i][j].setPiece();
                else this.boardSquares[i][j].setPiece(this.board.getPiece(i, j).toString());

            }
        }
    }

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

    public void showDialog(boolean won) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Game Over");
        dialog.setContentText(won ? "You WON!" : "You Lost!");
        ButtonType homeButton = new ButtonType("HOME", ButtonBar.ButtonData.OK_DONE);
        ButtonType rematchButton = new ButtonType("REMATCH", ButtonBar.ButtonData.YES);
        dialog.getDialogPane().getButtonTypes().addAll(homeButton, rematchButton);
        dialog.showAndWait();
        dialog.setResultConverter((buttonType -> {
            if (buttonType == rematchButton) {
                board.resetBoard();
                updateBoard();
            } else {
                gotoHome();
            }
            return null;
        }));
    }


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

    private void setHighlight(ArrayList<Move> moves, boolean light) {
        for (Move mv : moves) {
            this.boardSquares[mv.getDestination().getRow()][mv.getDestination().getColumn()].setHighlight(light);
        }
    }

    private void turnOffAllHighlights() {
        for (BoardSquare[] boardSquare : this.boardSquares) {
            for (BoardSquare bs : boardSquare) {
                bs.setHighlight(false);
            }
        }
    }

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
