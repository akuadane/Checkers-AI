package com.company.ReinforcementLearning;

import com.company.models.Board;
import com.company.models.piece.Piece;
import com.company.models.players.Player;

public class ActionResult {
    private Board state;

    private double reward;
    private boolean done;
    private Piece.PieceOwner winner;



    public ActionResult(Board state, double reward, Piece.PieceOwner winner, boolean done){
        this.state = state;
        this.reward = reward;
        this.done = done;
        this.winner = winner;
    }
    public  ActionResult(){
        this.state = null;
        this.reward = 0;
        this.done = false;
        this.winner = null;
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

    public Piece.PieceOwner getWinner() {
        return winner;
    }
}
