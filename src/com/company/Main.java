package com.company;

import com.company.move.Move;
import com.company.move.RemovingMove;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Board board = new Board();
        board.display();
        board.findLegalMoves(PieceOwner.PLAYER1);

//        for(Move a: board.moveList){
//                System.out.println(String.valueOf(a));
//
//        }
        System.out.println("===================");
        board.makeMove(board.moveList.get(3));
        board.display();

    }
}