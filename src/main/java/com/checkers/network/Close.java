package com.checkers.network;

/**
 * Class Close signifies a message that closes the game
 */
public class Close extends Action {
    public int id;
    public int clientID;

    public Close(int id, int clientID) {
        this.id = id;
        this.clientID = clientID;
    }
}
