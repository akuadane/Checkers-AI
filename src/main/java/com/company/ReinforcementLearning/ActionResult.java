package com.company.ReinforcementLearning;

import com.company.models.Board;
import com.company.models.players.Player;

public class ActionResult {
    private Board state;

    private double reward;
    private boolean done;



    public ActionResult(Board state, double reward, boolean done){
        this.state = state;
        this.reward = reward;
        this.done = done;
    }
    public  ActionResult(){
        this.state = null;
        this.reward = 0;
        this.done = false;
    }

    public Board getState() {
        return new Board(state);
    }

    public double getReward() {
        return reward;
    }

    public boolean isDone() {
        return done;
    }
}
