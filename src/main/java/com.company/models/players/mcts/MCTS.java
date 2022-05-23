package com.company.models.players.mcts;

import com.company.models.Board;

import java.time.LocalTime;

public class MCTS {
    LocalTime stTime;
    final int MAX_SECONDS = 5;
    

    public void getBestMove(Board board){
        this.stTime = LocalTime.now();

        while(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==1){ //While time isn't up look for a good move
            
        }

    }
}
