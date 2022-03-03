package com.company;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Board board = new Board();
        board.display();

        board.findLegalMoves(PieceOwner.PLAYER2);

        for(int[] a: board.moveList){
            System.out.printf("%d %d %d %d\n",a[0],a[1],a[2],a[3]);
        }
       
    }
}
