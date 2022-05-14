package com.company.ReinforcementLearning;

import com.company.models.Board;
import com.company.models.move.Move;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import java.util.Map;

public class QTable {
    private final String DEFAULT_PATH = "src/checkers_db.db";

    private  DB db;
    private Map<String,int[]> map;
    public QTable(String name){
        db = DBMaker.fileDB(DEFAULT_PATH).make();
        map = db.hashMap(name,Serializer.STRING,Serializer.INT_ARRAY).createOrOpen();

    }
    public QTable(){
    this("v1");
    }


    public Move getAction(Board state){
        return null;
    }
    public double getMaxActionScore(Board state){
        return 0;
    }
    public void setActionScore(Board state, double score){}
}
