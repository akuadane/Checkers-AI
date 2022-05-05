package com.checkersai.models;

import com.checkersai.models.players.MinMaxAIPlayer;
import com.checkersai.models.players.Player;
import com.checkersai.models.players.RandomPlayer;
import com.checkersai.models.players.RatioAIPlayer;

public class PlayerFactory {

    public static Player[] getAllPlayers(){
        return new Player[]{new RandomPlayer(),
                new MinMaxAIPlayer(),
                new RatioAIPlayer()};
    }
}
