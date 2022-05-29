package com.checkers.network;

import com.checkers.models.move.Move;

public class MakeMove extends Action {
    public Move move;
    public String gameID;
    public int playerID;

    public MakeMove(Move move, String gameID, int playerID) {
        this.move = move;
        this.gameID = gameID;
        this.playerID = playerID;
    }
}
