package com.company.gui;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.move.Position;
import com.company.models.piece.Piece;

import com.company.models.players.AlphaBetaMinMaxAIPlayer;
import com.company.models.players.Player;
import com.company.models.players.RandomPlayer;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javafx.scene.Scene;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.util.ArrayList;

public class Checkers extends Application {
    private final int WIDTH =Board.BOARD_SIZE *  BoardSquare.SIZE;
    private final int HEIGHT =Board.BOARD_SIZE *  BoardSquare.SIZE;
    private Board board;
    private BoardSquare[][] boardSquares;
    private Position origin;
    Player aiPlayer;
    private boolean aiTurn=false;
    @Override
    public void start(Stage stage){
        this.aiPlayer= new AlphaBetaMinMaxAIPlayer();
        this.board = new Board();
        this.boardSquares = new BoardSquare[Board.BOARD_SIZE][Board.BOARD_SIZE];
        this.initBoardSquares();

        GridPane gridPane = new GridPane();
        gridPane.setPrefSize( WIDTH,HEIGHT);
        for (int i = 0; i < this.boardSquares.length; i++) {
            for (int j = 0; j < this.boardSquares.length; j++) {
                gridPane.add(this.boardSquares[i][j],j,i);
            }
        }

        Scene scene = new Scene(gridPane,WIDTH,HEIGHT);
        stage.setTitle("Checkers");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


    }
    public void initBoardSquares(){
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                BoardSquare square = new BoardSquare(i,j);
                if(this.board.getPiece(i,j)==null)
                    square.setPiece();
                else
                    square.setPiece(this.board.getPiece(i,j).toString());

                square.setHighlight(false);
                square.setOnAction(actionEvent -> boardClicked(square.getCoordinate()));
                this.boardSquares[i][j] = square;
            }
        }
    }
    public void updateBoard(){
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = (1 - i % 2); j < Board.BOARD_SIZE; j += 2) {

                if(this.board.getPiece(i,j)==null)
                    this.boardSquares[i][j].setPiece();
                else
                    this.boardSquares[i][j].setPiece(this.board.getPiece(i,j).toString());

            }
        }
    }
    public void boardClicked(Position clickedPos){

        if(aiTurn)
            return;

        if(this.origin==null){
            Piece p = board.getPiece(clickedPos);
            if(p==null)
                return;

            if(p.owner==board.getTurn()){
                this.origin = clickedPos;
                this.setHighlight(this.board.reachablePositions(clickedPos),true);
            }
        }else{
            Piece p = board.getPiece(clickedPos);
            if(p!=null && p.owner==board.getTurn()){
                this.origin = clickedPos;
                this.turnOffAllHighlights();
                this.setHighlight(this.board.reachablePositions(this.origin),true);
                return;
            }

            Move move = new Move(this.origin,clickedPos);
            try {
                this.board.makeMove(move);
                updateBoard();

                isThereWinner();


                if(aiPlayer!=null){ //AI will make a move

                    this.aiMove();

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

    public void aiMove(){
        this.aiTurn=true;
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                Move aiMove = aiPlayer.makeMove(new Board(board));
                board.makeMove(aiMove);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateBoard();
                    }
                });

                return null;
            }

            @Override
            protected void done() {
                super.done();
                aiTurn=false;
                isThereWinner();
            }
        };

         Thread newTask = new Thread(task);
         newTask.start();

    }

    private void setHighlight(ArrayList<Move> moves, boolean light){
        for (Move mv :
                moves) {
            this.boardSquares[mv.getDestination().getRow()][mv.getDestination().getColumn()].setHighlight(light);
        }
    }

    private void turnOffAllHighlights(){
        for (BoardSquare[] boardSquare : this.boardSquares){
            for (BoardSquare bs :
                    boardSquare) {
                bs.setHighlight(false);
            }
        }
    }

   public void isThereWinner(){
       Piece.PieceOwner winner = this.board.isGameOver();
       if(winner!=null){
           //   JOptionPane.showMessageDialog(this, winner + " won.", "WINNER ALERT " , JOptionPane.INFORMATION_MESSAGE);
           System.out.println(winner + " is the winner");
       }
   }


    public static void main(String[] args) {

        launch(args);
    }



}
