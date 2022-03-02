package com.company;

public class MinMaxAIPlayer extends Player implements AIPlayer{
    MinMaxAIPlayer(String name) {
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

    private double min(Board prevBoard){
        return 0;
    }
    private double max(Board prevBoard){
        return 0;
    }
}
