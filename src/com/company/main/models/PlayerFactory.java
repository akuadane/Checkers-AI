package com.company.main.models;

import com.company.main.models.players.MinMaxAIPlayer;
import com.company.main.models.players.Player;
import com.company.main.models.players.RandomPlayer;
import com.company.main.models.players.RatioAIPlayer;

public class PlayerFactory {

    public static Player[] getAllPlayers(){
        return new Player[]{new RandomPlayer(),
                new MinMaxAIPlayer(),
                new RatioAIPlayer()};
    }
}
