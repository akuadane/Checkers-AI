package com.company.main.models.players;
import com.company.main.models.Board;
import com.company.main.models.exceptions.InValidMove;
import com.company.main.models.piece.PieceOwner;
import  com.company.main.models.move.Move;
public abstract class Player {
    String name;
    public PieceOwner myTurn;
    Player(String name,PieceOwner myTurn){
        this.name = name;
        this.myTurn = myTurn;
    }
    Player(String name){
        this.name = name;
        this.myTurn = PieceOwner.PLAYER1;
    }

    public abstract Move makeMove(Board board) throws InValidMove;

    @Override
    public String toString() {
        return name;
    }
}

