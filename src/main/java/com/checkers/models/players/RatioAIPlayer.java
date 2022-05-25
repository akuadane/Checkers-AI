package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;

public class RatioAIPlayer extends Player implements AIPlayer {
    RatioAIPlayer(String name, Piece.PieceOwner myTurn) {
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

    private double min(Board prevBoard, double alpha, double beta){
        return 0;
    }
    private double max(Board prevBoard, double alpha, double beta){
        return 0;
    }
}
