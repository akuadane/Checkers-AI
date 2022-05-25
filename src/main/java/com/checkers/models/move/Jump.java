package com.checkers.models.move;

import java.util.ArrayList;
import java.util.List;

public class Jump extends Move {
    public List<Position> toBeRemoved;

    public Jump(Position origin, Position destination) {
        super(origin,destination);
        toBeRemoved = new ArrayList<>();
    }

    //TODO create an exception for when the size of the array is > 4
    public void addToBeRemovedSquare(Position remove){toBeRemoved.add(remove);}

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
                if(!toBeRemoved.get(i).equals(that.toBeRemoved.get(i)))
                    return false;

       }
       return true;
    }
    @Override
    public String toString() {
        String thisString = ",removing ";
        for (Position remove :
                toBeRemoved) {
            thisString += String.format("[%d,%d] ", remove.getRow(), remove.getColumn());
        }
        return super.toString() + thisString;
    }
}
