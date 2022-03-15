package com.company.main.controller;

import com.company.main.models.Board;
import com.company.main.models.exceptions.InValidMove;
import com.company.main.models.move.Move;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.players.Player;

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

public Player play() throws InValidMove {
    while (true){
        board.display();
        Move playerMove = playerInTurn.makeMove(board.clone());
        board.makeMove(playerMove,playerInTurn.myTurn);

        System.out.println("------------------------------ "+playerInTurn+" "+playerMove);


        PieceOwner winner = board.isGameOver();
        if(winner!=null){
            System.out.println("Game over!!!");
            Player winnerPlayer = ((player1.myTurn==winner)?(player1):(player2));
            System.out.println(winnerPlayer + " is the winner");
            board.display();
            return winnerPlayer;
        }

        playerInTurn = (playerInTurn==player1)?player2:player1;
    }

}

}
