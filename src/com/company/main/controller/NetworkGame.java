package com.company.main.controller;

import com.company.main.models.players.Player;

import java.net.Socket;

public class NetworkGame extends Game{

    Socket socket;
    public NetworkGame(Player player1, Player player2) {
        super(player1, player2);
    }


    @Override
    public Player play(){return null;}

    public void acceptSocket(){}
}
