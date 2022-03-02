package com.company;

public class RandomPlayer extends Player{
    RandomPlayer(String name) {
        super(name);
    }

    @Override
    int[] makeMove(Board board) {
        return new int[0];
    }
}
