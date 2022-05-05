package com.checkersai.models.players;

import com.checkersai.models.Board;
import com.checkersai.models.move.Move;
import com.checkersai.models.piece.PieceOwner;

public class RatioAIPlayer extends Player implements AIPlayer{
    RatioAIPlayer(String name, PieceOwner myTurn) {
        super(name,myTurn);
    }

    public RatioAIPlayer(){
        super("RatioAIPlayer");
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
