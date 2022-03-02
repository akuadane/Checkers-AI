package com.company;

public class IterativeDeepeningAIPlayer extends Player implements AIPlayer{
    IterativeDeepeningAIPlayer(String name) {
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

    private double min(Board prevBoard,double alpha, double beta, int iteration, int time){
        return 0;
    }
    private double max(Board prevBoard, double alpha, double beta, int iteration, int time){
        return 0;
    }
}
