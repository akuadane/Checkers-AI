package com.company;

import com.company.move.Move;

public class RandomPlayer extends Player{
    RandomPlayer(String name,PieceOwner myTurn) {
        super(name,myTurn);
    }

    @Override
    Move makeMove(Board board) {
        return null;
    }
}
