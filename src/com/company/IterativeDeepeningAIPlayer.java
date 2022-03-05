package com.company;

import com.company.move.Move;

public class IterativeDeepeningAIPlayer extends Player implements AIPlayer{
    IterativeDeepeningAIPlayer(String name,PieceOwner myTurn) {
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

    private double min(Board prevBoard,double alpha, double beta, int iteration, int time){
        return 0;
    }
    private double max(Board prevBoard, double alpha, double beta, int iteration, int time){
        return 0;
    }
}
