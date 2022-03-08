package com.company.main.models.players;
import com.company.main.models.Board;
import com.company.main.models.piece.PieceOwner;
import  com.company.main.models.move.Move;
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

