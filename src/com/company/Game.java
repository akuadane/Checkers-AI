package com.company;

import com.company.move.Move;

import java.util.List;

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
        board.display();
        Move playerMove = playerInTurn.makeMove(board.copy());
        board.makeMove(playerMove,playerInTurn.myTurn);
        PieceOwner winner = board.isGameOver();
        if(winner!=null){
            System.out.println("Game over!!!");
            System.out.println(((player1.myTurn==winner)?(player1):(player2)) + " is the winner");
            board.display();

            break;
        }

        Thread.sleep(100); // TODO remove this
        System.out.println("------------------------------ "+playerInTurn+" "+playerMove);
        playerInTurn = (playerInTurn==player1)?player2:player1;
    }


}

}
