package com.company.move;

import java.util.ArrayList;
import java.util.List;

public class Jump extends Move{
    public List<int[]> toBeRemoved;

    public Jump(int[] movement) {
        super(movement);
        toBeRemoved = new ArrayList<>();
    }

    //TODO create an exception for when the size of the array is > 4
    public void addToBeRemovedSquare(int[] remove){
        if(remove.length==2)
            toBeRemoved.add(remove);
    }

    @Override
    public boolean equals(Object o) {
        if(o==null)
            return false;
        if (!(o instanceof Jump))
           return false;
        if(this==o)
            return true;
       Jump that = (Jump) o;

       if(!super.equals(that))
           return false;
       if(toBeRemoved.size()!=that.toBeRemoved.size())
           return false;

       for(int i=0;i<toBeRemoved.size();i++){
            for(int j=0;j<4;j++){
                if(toBeRemoved.get(i)[j]!=that.toBeRemoved.get(i)[j])
                    return false;
            }
       }
       return true;
    }
    @Override
    public String toString() {
        String thisString = ",removing ";
        for (int[] remove :
                toBeRemoved) {
            thisString += String.format("[%d,%d]", remove[0], remove[1]);
        }
        return super.toString() + thisString;
    }
}
