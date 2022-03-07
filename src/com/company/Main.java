package com.company;

import com.company.move.Move;

public class Main {

    public static void main(String[] args) throws InterruptedException {
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

        Game game = new Game(new RandomPlayer("Aku",PieceOwner.PLAYER1),new RandomPlayer("Tse",PieceOwner.PLAYER2));
        game.play();


    }
}