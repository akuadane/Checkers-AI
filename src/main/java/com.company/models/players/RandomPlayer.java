package com.company.models.players;

import com.company.models.Board;
import com.company.models.move.Move;
import com.company.models.piece.Piece;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player {
    Random random;
    public RandomPlayer(String name, Piece.PieceOwner myTurn) {
        super(name,myTurn);
        this.random = new Random();
    }

    public RandomPlayer(){
        this("Random Player", Piece.PieceOwner.PLAYER2);
    }
    @Override
    public Move makeMove(Board board) {
        List<Move> moveList = board.reachablePositionsByPlayer(myTurn);
        int mv = random.nextInt(moveList.size());
        return moveList.get(mv);
    }
}
