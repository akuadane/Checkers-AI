package com.company;

import com.company.move.Move;

public class BreadthFirstSearchPlayer extends Player implements AIPlayer{
    BreadthFirstSearchPlayer(String name,PieceOwner myTurn) {
        super(name,myTurn);
    }



    @Override
    public double evalBoard(Board board) {
        return 0;
    }

    @Override
    Move makeMove(Board board) {
        return null;
    }

    public int[] search(Board prevBoard, int iteration, int time){
        return null;
    }
}
