package com.checkers.network;

public class JoinGame extends Action {
    public String id;
    public int playerID;
    public String name;

    public JoinGame(String id, int playerID, String name) {
        this.id = id;
        this.playerID = playerID;
        this.name = name;
    }
}
