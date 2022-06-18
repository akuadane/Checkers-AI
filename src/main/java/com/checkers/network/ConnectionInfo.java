package com.checkers.network;

/**
 * Class ConnectionInfo represents connection information send between players over a network
 */
public class ConnectionInfo extends Action {
    public String playerName;

    public ConnectionInfo(String playerName) {
        this.playerName = playerName;
    }
}
