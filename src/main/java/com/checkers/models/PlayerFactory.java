package com.checkers.models;

import com.checkers.models.players.*;
import com.checkers.models.players.mcts.MCTSPlayer;

public class PlayerFactory {

    public static Player[] getAllPlayers(){
        return new Player[]{
                new RandomPlayer(),
               new MinMaxAIPlayer(),
            new AlphaBetaMinMaxAIPlayer(),
                new BackRowAIPlayer(),
          //      new ReinforcedMinMax(),
               new MCTSPlayer(),
        };
    }
}
