package com.company;

public class PhysicalPlayer extends Player{

    PhysicalPlayer(String name,PieceOwner myTurn) {
        super(name,myTurn);
    }

    @Override
    int[] makeMove(Board board) {
        //TODO receive the move from the click of mouse
        return new int[0];
    }
}
