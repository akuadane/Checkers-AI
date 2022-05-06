package com.company.models.players;

import com.company.models.Board;
import com.company.models.move.Move;
import com.company.models.piece.Piece;

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
