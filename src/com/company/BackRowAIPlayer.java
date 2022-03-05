package com.company;

public class BackRowAIPlayer extends Player implements AIPlayer{
    BackRowAIPlayer(String name,PieceOwner myTurn) {
        super(name,myTurn);
    }


    @Override
    public double evalBoard(Board board) {
        return 0;
    }

    @Override
    int[] makeMove(Board board) {
        return new int[0];
    }

    private double min(Board prevBoard,double alpha, double beta){
        return 0;
    }
    private double max(Board prevBoard, double alpha, double beta){
        return 0;
    }
}
