package com.checkers.network;

public class CreateGame extends Action {
    public int id;
    public int playerID;
    public String name;

    public CreateGame(int id, int playerID, String name) {
        this.id = id;
        this.playerID = playerID;
        this.name = name;
    }
}
