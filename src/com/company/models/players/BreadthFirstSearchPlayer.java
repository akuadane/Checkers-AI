package com.company.models.players;

import com.company.models.Board;
import com.company.models.piece.PieceOwner;
import com.company.models.move.Move;

public class BreadthFirstSearchPlayer extends Player implements AIPlayer{
    BreadthFirstSearchPlayer(String name, PieceOwner myTurn) {
        super(name,myTurn);
    }



    @Override
    public double evalBoard(Board board) {
        return 0;
    }

    @Override
    public Move makeMove(Board board) {
        return null;
    }

    public int[] search(Board prevBoard, int iteration, int time){
        return null;
    }
}
