package com.checkers.ReinforcementLearning;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.AlphaBetaMinMaxAIPlayer;
import com.checkers.models.players.RandomPlayer;

import java.util.List;
import java.util.Random;

public class TrainingGround {
    private static final double LEARNING_RATE=0.1;
    private static final double DISCOUNT=0.95;
    private static final int EPISODES = 20_000;
    private static final int SHOW=10;
    private static double epsilon = 0.5;
    private static int START_EPSILON_DECAYING=1;
    private static int END_EPSILON_DECAYING= EPISODES/2;
    private static double epsilon_decay_value = epsilon / ( END_EPSILON_DECAYING - START_EPSILON_DECAYING);
    public static void main(String[] args) {

            CheckersEnvironment env = new CheckersEnvironment(new RandomPlayer("AI", Piece.PieceOwner.PLAYER1));

            QTable qTable = new QTable("Four-Player");

        Random random = new Random();
        for (int i = 0; i < EPISODES+1; i++) {
            ActionResult result = new ActionResult();
            Board state = env.randomReset(4);
            System.out.println(i+"\r");
            while ( !result.isDone()){
                if(i%SHOW==0){
                    env.display();
                    System.out.println("=========================");
                }
                int actionIndex = qTable.getAction(state);
                List<Move> possibleMoves = state.reachablePositionsByPlayer();

                if(random.nextDouble()<=epsilon ){  // introduces randomness
                    actionIndex = random.nextInt(0,possibleMoves.size());
                }
                Move action = possibleMoves.get(actionIndex);

                try {
                    ActionResult newState = env.takeAction(action);
                    if(!newState.isDone()){
                        double max_future = qTable.getMaxActionScore(newState.getState());
                        double current_q = qTable.getMaxActionScore(state);
                        double new_q = (1-LEARNING_RATE) * current_q + LEARNING_RATE * (newState.getReward() + DISCOUNT* max_future);

                        qTable.setActionScore(state,actionIndex, new_q);
                        // System.out.println(current_q + " "+new_q +" "+max_future);

                    }else{
                        Piece.PieceOwner winner = newState.getWinner();
                        if(winner== Piece.PieceOwner.PLAYER2){
                            System.out.println("Won at episode: "+i);
                            qTable.setActionScore(state,actionIndex,CheckersEnvironment.HIGHEST_REWARD);
                        }
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
            if (START_EPSILON_DECAYING <= i && i <= END_EPSILON_DECAYING)
                epsilon -= epsilon_decay_value;
        }


    }
}
