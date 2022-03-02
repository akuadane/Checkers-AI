package com.company;

public class BreadthFirstSearchPlayer extends Player implements AIPlayer{
    BreadthFirstSearchPlayer(String name) {
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

    public int[] search(Board prevBoard, int iteration, int time){
        return new int[0];
    }
}
