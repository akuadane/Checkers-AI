package com.company.ReinforcementLearning;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Piece;
import com.company.models.players.AlphaBetaMinMaxAIPlayer;

public class TrainingGround {
    private static final double LEARNING_RATE=0.1;
    private static final double DISCOUNT=0.95;
    private static final int EPISODES =20_000;
    public static void main(String[] args) {

            CheckersEnvironment env = new CheckersEnvironment(new AlphaBetaMinMaxAIPlayer());
            Board state = env.reset();
            QTable qTable = new QTable();
            ActionResult result = new ActionResult();

            while ( !result.isDone()){
                Move action = qTable.getAction(state);
                try {
                    ActionResult newState = env.takeAction(action);
                    if(!newState.isDone()){
                        double max_future = qTable.getMaxActionScore(newState.getState());
                        double current_q = qTable.getMaxActionScore(state);
                        double new_q = (1-LEARNING_RATE) * current_q + LEARNING_RATE * (newState.getReward() + DISCOUNT* max_future);

                        qTable.setActionScore(state,new_q);

                    }else{
                        if(state.isGameOver()== Piece.PieceOwner.PLAYER1)
                            qTable.setActionScore(state,0);
                    }
                    result = newState;
                    state = result.getState();

                } catch (InValidMove e) {
                    System.out.println(e.getMessage());
                } catch (CloneNotSupportedException e) {
                   System.out.println(e.getMessage());
                }
            }
    }
}
