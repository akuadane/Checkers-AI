package com.company.ReinforcementLearning;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Piece;
import com.company.models.players.AlphaBetaMinMaxAIPlayer;

public class TrainingGround {
    private static final double LEARNING_RATE=0.1;
    private static final double DISCOUNT=0.95;
    private static final int EPISODES = 20_000;
    public static void main(String[] args) {

            CheckersEnvironment env = new CheckersEnvironment(new AlphaBetaMinMaxAIPlayer("AI", Piece.PieceOwner.PLAYER1));
            Board state = env.reset();
            QTable qTable = new QTable();
            ActionResult result = new ActionResult();

            while ( !result.isDone()){
                env.display();
                int actionIndex = qTable.getAction(state);
                Move action = state.reachablePositionsByPlayer(state.getTurn()).get(actionIndex);
                try {
                    ActionResult newState = env.takeAction(action);
                    if(!newState.isDone()){
                        double max_future = qTable.getMaxActionScore(newState.getState());
                        double current_q = qTable.getMaxActionScore(state);
                        double new_q = (1-LEARNING_RATE) * current_q + LEARNING_RATE * (newState.getReward() + DISCOUNT* max_future);

                        qTable.setActionScore(state,actionIndex, new_q);

                    }else{
                        Piece.PieceOwner winner = state.isGameOver();
                        if(winner== Piece.PieceOwner.PLAYER2)
                            qTable.setActionScore(state,actionIndex,CheckersEnvironment.HIGHEST_REWARD);
                        else
                            qTable.setActionScore(state,actionIndex,CheckersEnvironment.LOWEST_REWARD);

                    }
                    result = newState;
                    state = result.getState();

                } catch (InValidMove e) {
                    System.out.println(e.getMessage());
                } catch (CloneNotSupportedException e) {
                   System.out.println(e.getMessage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
    }
}
