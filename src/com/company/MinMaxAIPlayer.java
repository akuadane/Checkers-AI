package com.company;

import com.company.move.Move;

public class MinMaxAIPlayer extends Player implements AIPlayer{
    MinMaxAIPlayer(String name,PieceOwner myTurn) {
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

    private double min(Board prevBoard){
        return 0;
    }
    private double max(Board prevBoard){
        return 0;
    }
}
