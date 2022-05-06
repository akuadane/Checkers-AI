package com.company.models;

import com.company.models.players.MinMaxAIPlayer;
import com.company.models.players.Player;
import com.company.models.players.RandomPlayer;
import com.company.models.players.RatioAIPlayer;

public class PlayerFactory {

    public static Player[] getAllPlayers(){
        return new Player[]{new RandomPlayer(),
                new MinMaxAIPlayer(),
                new RatioAIPlayer()};
    }
}
