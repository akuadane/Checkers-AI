package com.company.models.players;

import com.company.models.Board;
import com.company.models.move.Move;
import com.company.models.piece.Piece;

public class IterativeDeepeningAIPlayer extends Player implements AIPlayer {
    IterativeDeepeningAIPlayer(String name, Piece.PieceOwner myTurn) {
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

    private double min(Board prevBoard, double alpha, double beta, int iteration, int time){
        return 0;
    }
    private double max(Board prevBoard, double alpha, double beta, int iteration, int time){
        return 0;
    }
}
