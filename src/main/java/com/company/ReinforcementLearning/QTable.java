package com.company.ReinforcementLearning;

import com.company.models.Board;
import com.company.models.move.Move;
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
               actions[i] = random.nextDouble(CheckersEnvironment.LOWEST_REWARD,CheckersEnvironment.HIGHEST_REWARD);
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
    public double getMaxActionScore(Board state){
        return 0;
    }
    public void setActionScore(Board state, double score) throws Exception {
        double[] actions = this.map.get(state.toString());
        if(actions==null)
            throw new Exception("Entry not found.");

    }
}
