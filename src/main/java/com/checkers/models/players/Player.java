package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;

import java.util.concurrent.Future;

public abstract class Player {
    String name;
    String opponentName;

    public Piece.PieceOwner myTurn;

    Player(String name, Piece.PieceOwner myTurn) {
        this.name = name;
        this.myTurn = myTurn;
    }

    Player(String name) {
        this.name = name;
        this.myTurn = Piece.PieceOwner.PLAYER2;
    }

    public abstract Move makeMove(Board board) throws InValidMove, CloneNotSupportedException;

    public boolean makeMove(Move move) throws InValidMove {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Player))
            return false;

        Player p = (Player) obj;
        return p.name.equals(this.name) && p.myTurn.equals(this.myTurn);

    }
}

