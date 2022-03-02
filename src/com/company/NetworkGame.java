package com.company;

import java.net.Socket;

public class NetworkGame extends Game{

    Socket socket;
    public NetworkGame(Player player1, Player player2) {
        super(player1, player2);
    }


    @Override
    public void play(){}

    public void acceptSocket(){}
}
