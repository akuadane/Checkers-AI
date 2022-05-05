package com.checkersai.models.players;

import com.checkersai.models.Board;
import com.checkersai.models.move.Move;
import com.checkersai.models.piece.PieceOwner;

public class BreadthFirstSearchPlayer extends Player implements AIPlayer{
    public BreadthFirstSearchPlayer(String name, PieceOwner myTurn) {
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
