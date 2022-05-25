package com.checkers.models;

import com.checkers.models.players.MinMaxAIPlayer;
import com.checkers.models.players.Player;
import com.checkers.models.players.RandomPlayer;
import com.checkers.models.players.RatioAIPlayer;

public class PlayerFactory {

    public static Player[] getAllPlayers(){
        return new Player[]{new RandomPlayer(),
                new MinMaxAIPlayer(),
                new RatioAIPlayer()};
    }
}
