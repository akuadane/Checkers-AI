package com.company;

public class RandomPlayer extends Player{
    RandomPlayer(String name,PieceOwner myTurn) {
        super(name,myTurn);
    }

    @Override
    int[] makeMove(Board board) {
        return new int[0];
    }
}
