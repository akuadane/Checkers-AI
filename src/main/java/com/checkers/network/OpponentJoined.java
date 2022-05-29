package com.checkers.network;

public class OpponentJoined extends Action {
    public String opponentName;

    public OpponentJoined(String opponentName) {
        this.opponentName = opponentName;
    }
}
