package com.company;
import  com.company.move.Move;
public abstract class Player {
    String name;
    PieceOwner myTurn;
    Player(String name,PieceOwner myTurn){
        this.name = name;
        this.myTurn = myTurn;
    }

    abstract Move makeMove(Board board);

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}

