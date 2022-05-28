package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;

public class RemotePlayer extends Player {
    RemotePlayer(String name, Piece.PieceOwner myTurn) {
        super(name, myTurn);
    }

    @Override
    public Move makeMove(Board board) {
        // TODO implement this method
        return null;
    }
}
