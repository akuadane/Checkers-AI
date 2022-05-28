package com.checkers.ReinforcementLearning;

import com.checkers.models.Board;
import com.checkers.models.move.Move;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class QTable {
    private final String DEFAULT_PATH = "src/checkers_db.db";

    private  DB db;
    private Map<String,double[]> map;
    public QTable(String name){
        db = DBMaker.fileDB(DEFAULT_PATH).make();
        map = db.hashMap(name,Serializer.STRING,Serializer.DOUBLE_ARRAY).createOrOpen();
        System.out.println(map.size());
    }
    public QTable(){
    this("v1");
    }


    public int getAction(Board state){
        double[] actions = this.map.get(state.toString());

        if(actions==null){
            //TODO initialize the int array with random values
            List<Move> possibleMoves = state.reachablePositionsByPlayer(state.getTurn());
            actions = new double[possibleMoves.size()];

            Random random = new Random();
            for (int i = 0; i < actions.length; i++) {
               actions[i] = random.nextDouble(CheckersEnvironment.LOWEST_REWARD,CheckersEnvironment.HIGHEST_REWARD+0.000001);

            }

            this.map.put(state.toString(),actions);
            db.commit();
        }
            double max = Double.MIN_VALUE;
            int maxIndex = 0;
            for (int i = 0; i < actions.length; i++) {
                if(actions[i]>max){
                    max = actions[i];
                    maxIndex = i;
                }
            }
            return maxIndex;
    }
    public double getMaxActionScore(Board state) throws Exception {
        int index = this.getAction(state);
        double[] actions = this.map.get(state.toString());
//        if(actions==null)
//            throw  new Exception("No entry found.");
//
//        double max = Double.MIN_VALUE;
//        int maxIndex = 0;
//        for (int i = 0; i < actions.length; i++) {
//            if(actions[i]>max){
//                max = actions[i];
//                maxIndex = i;
//            }
//        }
//        return max;

        return actions[index];

    }
    public void setActionScore(Board state,int index, double score) throws Exception {
        double[] actions = this.map.get(state.toString());
        if(actions==null)
            throw new Exception("Entry not found.");

        actions[index] = score;

        this.map.put(state.toString(),actions);
        db.commit();
    }
}
