package com.company.main.network;

public class JoinGame extends Action {
    public int id;
    public int playerID;
    public String name;

    public JoinGame(int id, int playerID, String name) {
        this.id = id;
        this.playerID = playerID;
        this.name = name;
    }
}
