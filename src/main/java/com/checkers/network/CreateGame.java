package com.checkers.network;

/**
 * Class CreateGame represents an action to create a remote game.
 */
public class CreateGame extends Action {
    public String id;
    public int playerID;
    public String name;

    public CreateGame(String id, int playerID, String name) {
        this.id = id;
        this.playerID = playerID;
        this.name = name;
    }
}
