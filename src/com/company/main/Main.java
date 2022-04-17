package com.company.main;

import com.company.main.controller.Game;
import com.company.main.controller.Tournament;
import com.company.main.models.Board;
import com.company.main.models.exceptions.InValidMove;
import com.company.main.models.move.Move;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.players.MinMaxAIPlayer;
import com.company.main.models.players.PhysicalPlayer;
import com.company.main.models.players.RandomPlayer;

import java.time.LocalTime;

public class Main {

    public static void main(String[] args) throws InterruptedException, InValidMove, CloneNotSupportedException {
	// write your code here
//        Board board = new Board();
//        board.display();
//
//
//        for(Move a:board.findLegalMoves(PieceOwner.PLAYER1)){
//                System.out.println(String.valueOf(a));
//        }
//        for(Move a: board.jumpList){
//            System.out.println(String.valueOf(a));
//        }
//        System.out.println("===================");
//        board.makeMove(board.moveList.get(3));
//        board.display();

        //Game game = new Game(new MinMaxAIPlayer("Aku", PieceOwner.PLAYER1),new RandomPlayer("AI",PieceOwner.PLAYER2));
        //game.play();

        Tournament tr = new Tournament();
        tr.playOff(1);

    }
}