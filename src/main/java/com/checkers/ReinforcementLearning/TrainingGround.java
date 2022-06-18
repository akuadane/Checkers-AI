package com.checkers.ReinforcementLearning;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.RandomPlayer;


import java.io.*;
import java.util.*;

public class TrainingGround {
    private static final double LEARNING_RATE=0.1;
    private static final double DISCOUNT=0.95;
    private static final int EPISODES = 100;
    private static final int SHOW=10;
    private static double epsilon = 0.5;
    private static int START_EPSILON_DECAYING=1;
    private static int END_EPSILON_DECAYING= EPISODES/2;
    private static double epsilon_decay_value = epsilon / ( END_EPSILON_DECAYING - START_EPSILON_DECAYING);
    public static void main(String[] args) {

            CheckersEnvironment env = new CheckersEnvironment(new RandomPlayer("AI", Piece.PieceOwner.PLAYER1));

            QTable qTable = new QTable("Four-Player");

            Random random = new Random();
        PrintWriter writer;
        try {
         writer = new PrintWriter(new FileOutputStream("stat.csv"));
          writer.println("ep,min,max,avg");

        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        double totalRewards = 0;
            double minReward = Double.MAX_VALUE;
            double maxReward = Double.MIN_VALUE;
//            Map<String,List<Double>> stat = new HashMap<>(){
//                {
//                    put("ep",new ArrayList<>());
//                    put("avg",new ArrayList<>());
//                    put("min",new ArrayList<>());
//                    put("max",new ArrayList<>());
//
//                }
//            };
        for (int i = 0; i < EPISODES; i++) {
            double episodeReward = 0;
            ActionResult result = new ActionResult();
            Board state = env.randomReset(4);
            System.out.print(i+"\r");
            while ( !result.isDone()){
                if(i%SHOW==0){
                   // env.display();
                    //System.out.println("=========================");
                }
                int actionIndex = qTable.getAction(state);
                List<Move> possibleMoves = state.reachablePositionsByPlayer();

//                if(random.nextDouble()<=epsilon ){  // introduces randomness
//                    actionIndex = random.nextInt(0,possibleMoves.size());
//                }
                Move action = possibleMoves.get(actionIndex);

                try {
                    ActionResult newState = env.takeAction(action);
                    episodeReward+=newState.getReward();
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

            totalRewards += episodeReward;
            if(episodeReward < minReward)
                minReward = episodeReward;
            if(episodeReward > maxReward)
                maxReward = episodeReward;

            if(i%SHOW==0){
                double av = totalRewards /SHOW;
//                stat.get("ep").add((double) i);
//                stat.get("min").add(minReward);
//                stat.get("min").add(maxReward);
//                stat.get("avg").add(av);

                writer.println(String.format("%f, %f, %f, %f,",(double)i,minReward,maxReward,av));
                writer.flush();
                totalRewards = 0;
                minReward = Double.MAX_VALUE;
                maxReward = Double.MIN_VALUE;

            }
        }
        writer.close();


    }
}
