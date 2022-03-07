package com.company.models.players;

import com.company.models.Board;
import com.company.models.piece.PieceOwner;
import com.company.models.move.Move;

public class AlphaBetaMinMaxAIPlayer extends Player implements AIPlayer{
    AlphaBetaMinMaxAIPlayer(String name, PieceOwner myTurn) {
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

    private double min(Board prevBoard,double alpha, double beta){
        return 0;
    }
    private double max(Board prevBoard, double alpha, double beta){
        return 0;
    }
}
