package com.checkers.network;

import com.checkers.models.move.Move;

public class MakeMove extends Action {
    public Move move;

    public MakeMove(Move move) {
        this.move = move;
    }
}
