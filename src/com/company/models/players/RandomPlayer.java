package com.company.models.players;

import com.company.models.Board;
import com.company.models.piece.PieceOwner;
import com.company.models.move.Move;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player{
    Random random;
    public RandomPlayer(String name, PieceOwner myTurn) {
        super(name,myTurn);
        this.random = new Random();
    }

    @Override
    public Move makeMove(Board board) {
        List<Move> moveList = board.findLegalMoves(myTurn);
        int mv = random.nextInt(moveList.size());
        return moveList.get(mv);
    }
}
