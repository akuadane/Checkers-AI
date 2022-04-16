package com.company.main.models.players;

import com.company.main.models.Board;
import com.company.main.models.move.Move;
import com.company.main.models.piece.PieceOwner;

public class BreadthFirstSearchPlayer extends Player implements AIPlayer {
    public BreadthFirstSearchPlayer(String name, PieceOwner myTurn) {
        super(name, myTurn);
    }


    @Override
    public double evalBoard(Board board) {
        return 0;
    }

    @Override
    public Move makeMove(Board board) {
        return null;
    }

    public int[] search(Board prevBoard, int iteration, int time) {
        return null;
    }
}
