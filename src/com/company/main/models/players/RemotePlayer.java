package com.company.main.models.players;

import com.company.main.models.Board;
import com.company.main.models.move.Move;
import com.company.main.models.piece.PieceOwner;

public class RemotePlayer extends Player {
    RemotePlayer(String name, PieceOwner myTurn) {
        super(name, myTurn);
    }

    @Override
    public Move makeMove(Board board) {
        // TODO implement this method
        return null;
    }
}
