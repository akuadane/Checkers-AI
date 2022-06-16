package com.checkers.gui;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.move.Position;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.*;
import com.checkers.models.prefs.Config;
import com.checkers.models.prefs.Level;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Checkers {
    private final int WIDTH = Board.BOARD_SIZE * BoardSquare.SIZE;
    private final int HEIGHT = Board.BOARD_SIZE * BoardSquare.SIZE;
    private Board board;
    private BoardSquare[][] boardSquares;
    private Position origin;
    private boolean myTurn = true;
    Player player;
    public Config config;
    public static Checkers instance;

    private Checkers() {

    }

    public synchronized static Checkers getInstance() {
        if (instance != null)
            return instance;
        instance = new Checkers();
        return instance;
    }

    public void start(Stage stage) throws Exception {
        config = (Config) (stage.getUserData());
        switch (config.getGameType()) {
            case COMPUTER -> this.player = getAgentByDifficulty(config.getLevel());
            case REMOTE -> {
                this.player = config.getPlayer();
                ((RemotePlayer) this.player).setOnMakeMoveCallbackFunc((mv) -> {
                    try {
                        board.makeMove(mv);
                        updateBoard();
                    } catch (InValidMove e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });
                ((RemotePlayer) this.player).setOnMakeMoveCallbackFunc(this::remoteMove);

            }
        }
        this.myTurn = this.player.myTurn == Piece.PieceOwner.PLAYER1;
        FXMLLoader fxmlLoader = new FXMLLoader(ChoosePlayer.class.getResource("/main.fxml"));
        this.board = new Board();
        this.boardSquares = new BoardSquare[Board.BOARD_SIZE][Board.BOARD_SIZE];
        this.initBoardSquares();

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(WIDTH, HEIGHT);
        for (int i = 0; i < this.boardSquares.length; i++) {
            for (int j = 0; j < this.boardSquares.length; j++) {
                gridPane.add(this.boardSquares[i][j], j, i);
            }
        }

        Platform.runLater(() -> {
            Scene scene = new Scene(gridPane, WIDTH, HEIGHT);
            stage.setTitle("Checkers");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }

    private Player getAgentByDifficulty(Level level) throws Exception {
        if (level == null)
            throw new Exception("Level cannot be null");
        Player player;
        switch (level) {
            case EASY -> player = new RandomPlayer();
            case HARD -> player = new AlphaBetaMinMaxAIPlayer();
            case MEDIUM -> player = new MinMaxAIPlayer();
            case EXPERT -> player = new BackRowAIPlayer();
            default -> player = new IterativeDeepeningAIPlayer("John Doe", Piece.PieceOwner.PLAYER2);
        }
        return player;
    }

    public void initBoardSquares() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                BoardSquare square = new BoardSquare(i, j);
                if (this.board.getPiece(i, j) == null)
                    square.setPiece();
                else
                    square.setPiece(this.board.getPiece(i, j).toString());

                square.setHighlight(false);
                square.setOnAction(actionEvent -> boardClicked(square.getCoordinate()));
                this.boardSquares[i][j] = square;
            }
        }
    }

    public void updateBoard() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = (1 - i % 2); j < Board.BOARD_SIZE; j += 2) {
                if (this.board.getPiece(i, j) == null)
                    this.boardSquares[i][j].setPiece();
                else
                    this.boardSquares[i][j].setPiece(this.board.getPiece(i, j).toString());

            }
        }
    }

    public void boardClicked(Position clickedPos) {
        if (!myTurn)
            return;

        Piece p = board.getPiece(clickedPos);
        if (this.origin == null) {
            if (p == null)
                return;

            if (p.owner == board.getTurn()) {
                this.origin = clickedPos;
                this.setHighlight(this.board.reachablePositions(clickedPos), true);
            }
        } else {
            if (p != null && p.owner == board.getTurn()) {
                this.origin = clickedPos;
                this.turnOffAllHighlights();
                this.setHighlight(this.board.reachablePositions(this.origin), true);
                return;
            }

            Move move = new Move(this.origin, clickedPos);
            try {
                this.board.makeMove(move);
                updateBoard();
                isThereWinner();
                if (player instanceof AlphaBetaMinMaxAIPlayer) { //AI will make a move
                    this.aiMove();
                }
                if (player instanceof RemotePlayer) {
                    this.myTurn = !this.myTurn;
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

    public void aiMove() {
        this.myTurn = !this.myTurn;
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
                myTurn = !myTurn;
                isThereWinner();
            }
        };

        Thread newTask = new Thread(task);
        newTask.start();

    }

    private void setHighlight(ArrayList<Move> moves, boolean light) {
        for (Move mv :
                moves) {
            this.boardSquares[mv.getDestination().getRow()][mv.getDestination().getColumn()].setHighlight(light);
        }
    }

    private void turnOffAllHighlights() {
        for (BoardSquare[] boardSquare : this.boardSquares) {
            for (BoardSquare bs :
                    boardSquare) {
                bs.setHighlight(false);
            }
        }
    }

    public void isThereWinner() {
        Piece.PieceOwner winner = this.board.isGameOver();
        if (winner != null) {
            //   JOptionPane.showMessageDialog(this, winner + " won.", "WINNER ALERT " , JOptionPane.INFORMATION_MESSAGE);
            System.out.println(winner + " is the winner");
        }
    }


}
