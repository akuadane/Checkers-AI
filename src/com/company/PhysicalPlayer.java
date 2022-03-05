package com.company;

import com.company.move.Move;

import java.util.List;


public class PhysicalPlayer extends Player{

    PhysicalPlayer(String name,PieceOwner myTurn) {
        super(name,myTurn);
    }

    @Override
    Move makeMove(Board board) {
        //TODO receive the move from the click of mouse

        return null;
    }
}
