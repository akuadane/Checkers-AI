package com.company.ReinforcementLearning;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Piece;
import com.company.models.players.Player;

import java.util.List;

public class CheckersEnvironment{
    public static final double LOWEST_REWARD = 0;
    public static double HIGHEST_REWARD = 2;
    private Board state;
    private Player player1;


    public CheckersEnvironment(Player player1){
        this.player1 = player1;
       this.reset();
    }

    public ActionResult takeAction(Move mv) throws InValidMove, CloneNotSupportedException {

        this.state.makeMove(mv);
        // TODO compare the prev and current board to make a reward

        Move p2Move = this.player1.makeMove(new Board(this.state));


        this.state.makeMove(p2Move);
        // TODO assess the board state and update reward

        return null;
    }
    public Board reset(){
        this.state = new Board();
        try {
            Move mv = this.player1.makeMove(new Board(this.state));
            this.state.makeMove(mv);
        } catch (InValidMove e) {
            throw new RuntimeException(e);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
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
