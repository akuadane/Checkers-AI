package com.checkersai.models.players;

import com.checkersai.models.Board;
import com.checkersai.models.move.Move;
import com.checkersai.models.piece.PieceOwner;

public class RemotePlayer extends Player{
    RemotePlayer(String name, PieceOwner myTurn) {
        super(name, myTurn);
    }

    @Override
    public Move makeMove(Board board) {
        // TODO implement this method
        return null;
    }
}
