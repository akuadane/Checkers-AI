package com.company;

public class AlphaBetaMinMaxAIPlayer extends Player implements AIPlayer{
    AlphaBetaMinMaxAIPlayer(String name) {
        super(name);
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
