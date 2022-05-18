package com.company.models.players;
import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Piece;

public abstract class Player {
    String name;
    public Piece.PieceOwner myTurn;
    Player(String name, Piece.PieceOwner myTurn){
        this.name = name;
        this.myTurn = myTurn;
    }
    Player(String name){
        this.name = name;
        this.myTurn = Piece.PieceOwner.PLAYER2;
    }

    public abstract Move makeMove(Board board) throws InValidMove, CloneNotSupportedException;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(!(obj instanceof Player))
            return false;

        Player p = (Player) obj;
        return p.name.equals(this.name) && p.myTurn.equals(this.myTurn);

    }
}

