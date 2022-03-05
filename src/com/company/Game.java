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

public void play(){
    while (true){
        List<Move> possibleMovements = board.findLegalMoves(playerInTurn.myTurn);

        Move playerMove = playerInTurn.makeMove(board.copy());
        board.makeMove(playerMove);

        playerInTurn = (playerInTurn==player1)?player2:player1;
    }


}

}
