package com.checkers.controller;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.Player;

public class Game {
Player player1;
Player player2;
Player playerInTurn;
Board board;

public Game(Player player1, Player player2){
    this.player1 = player1;
    this.player2= player2;
    this.playerInTurn = player1;
    this.board = new Board();
}
public Game(Player player1, Player player2,Board board){
    this.player1 = player1;
    this.player2 = player2;
    this.playerInTurn = (board.getTurn()==player1.myTurn)? player1: player2;
    this.board = board;
}

public Player play() throws InValidMove, CloneNotSupportedException {
    while (true){
        final long startTime = System.currentTimeMillis();
        board.display();
        System.out.println(playerInTurn+" is thinking...");
        Move playerMove = playerInTurn.makeMove(new Board(board));
        board.makeMove(playerMove);

        System.out.println("------------------------------ "+playerInTurn+" "+playerMove);


        Piece.PieceOwner winner = board.isGameOver();
        if(winner!=null){
            System.out.println("Game over!!!");
            Player winnerPlayer = ((player1.myTurn==winner)?(player1):(player2));
            System.out.println(winnerPlayer + " is the winner");
            board.display();
            return winnerPlayer;
        }

        playerInTurn = (playerInTurn.equals(player1))?player2:player1;
        final long duration = System.currentTimeMillis() - startTime;
        System.out.println(duration + " milli seconds");
    }

}

public Player playWithoutDebugging() throws InValidMove, CloneNotSupportedException {
    while(true){
        Piece.PieceOwner winner = board.isGameOver();
        if(winner!=null){
            Player winnerPlayer = ((player1.myTurn==winner)?(player1):(player2));
            return winnerPlayer;
        }

        Move playerMove = playerInTurn.makeMove(new Board(board));
        board.makeMove(playerMove);
        playerInTurn = (playerInTurn.equals(player1))?player2:player1;
    }

}

}
