package com.checkers.network;

import com.checkers.models.move.Move;

/**
 * Class MakeMove represents a move action performed by the sender
 */
public class MakeMove extends Action {
    public Move move;

    public MakeMove(Move move) {
        this.move = move;
    }
}
