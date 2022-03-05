package com.company;

import com.company.move.Move;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player{
    Random random;
    RandomPlayer(String name,PieceOwner myTurn) {
        super(name,myTurn);
        this.random = new Random();
    }

    @Override
    Move makeMove(Board board) {
        List<Move> moveList = board.findLegalMoves(myTurn);
        int mv = random.nextInt(moveList.size());
        return moveList.get(mv);
    }
}
