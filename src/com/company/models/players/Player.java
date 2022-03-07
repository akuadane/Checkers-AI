package com.company.models.players;
import com.company.models.Board;
import com.company.models.piece.PieceOwner;
import  com.company.models.move.Move;
public abstract class Player {
    String name;
    public PieceOwner myTurn;
    Player(String name,PieceOwner myTurn){
        this.name = name;
        this.myTurn = myTurn;
    }

    public abstract Move makeMove(Board board);

    @Override
    public String toString() {
        return name;
    }
}

