package com.checkersai.models.players;

import com.checkersai.models.Board;
import com.checkersai.models.move.Move;
import com.checkersai.models.piece.PieceOwner;

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
