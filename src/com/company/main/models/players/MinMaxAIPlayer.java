package com.company.main.models.players;

import com.company.main.models.Board;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.move.Move;

public class MinMaxAIPlayer extends Player implements AIPlayer{
    MinMaxAIPlayer(String name, PieceOwner myTurn) {
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

    private double min(Board prevBoard){
        return 0;
    }
    private double max(Board prevBoard){
        return 0;
    }
}
