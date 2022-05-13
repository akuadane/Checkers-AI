package com.company.ReinforcementLearning;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Piece;
import com.company.models.players.Player;

import java.util.List;

public class CheckersEnvironment{
    private Board state;
    private Player player2;


    public CheckersEnvironment(Player player2){
        this.player2 = player2;
    }

    public ActionResult takeAction(Move mv) throws InValidMove, CloneNotSupportedException {

        this.state.makeMove(mv);
        // TODO compare the prev and current board to make a reward

        Move p2Move = this.player2.makeMove(new Board(this.state));


        this.state.makeMove(p2Move);
        // TODO assess the board state and update reward

        return null;
    }
    public Board reset(){
        this.state = new Board();
        return new Board(state);
    }

    public double rewardFunc(){
        return 0;
    }
    public double rewardFunc(double preReward){
        return 0;
    }
    public void display(){
        this.state.display();
    }
    public List<Move> getActions(){
        return this.state.reachablePositionsByPlayer(this.state.getTurn());
    }

    public Piece.PieceOwner isGameOver(){
        return this.state.isGameOver();
    }

}
