package com.company.main.models.players;

import com.company.main.models.Board;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.move.Move;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player{
    Random random;
    public RandomPlayer(String name, PieceOwner myTurn) {
        super(name,myTurn);
        this.random = new Random();
    }

    public RandomPlayer(){
        this("Random Player",PieceOwner.PLAYER1);
    }
    @Override
    public Move makeMove(Board board) {
        List<Move> moveList = board.findLegalMoves(myTurn);
        int mv = random.nextInt(moveList.size());
        return moveList.get(mv);
    }
}
