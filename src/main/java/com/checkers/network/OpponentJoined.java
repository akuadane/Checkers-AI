package com.checkers.network;

/**
 * Class OpponentJoined signifies an action that opponent has joined the remote game.
 */
public class OpponentJoined extends Action {
    public String opponentName;

    public OpponentJoined(String opponentName) {
        this.opponentName = opponentName;
    }
}
