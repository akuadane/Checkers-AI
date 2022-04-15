package com.company.main.controller;

import com.company.main.models.Board;
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

public void play() throws InterruptedException {
    while (true){
        final long startTime = System.currentTimeMillis();
        board.display();
        Move playerMove = playerInTurn.makeMove(board.clone());
        board.makeMove(playerMove,playerInTurn.myTurn);

        System.out.println("------------------------------ "+playerInTurn+" "+playerMove);


        PieceOwner winner = board.isGameOver();
        if(winner!=null){
            System.out.println("Game over!!!");
            System.out.println(((player1.myTurn==winner)?(player1):(player2)) + " is the winner");
            board.display();
            break;
        }

        playerInTurn = (playerInTurn==player1)?player2:player1;
        final long duration = System.currentTimeMillis() - startTime;
        System.out.println(duration + "seconds");
    }


}

}
