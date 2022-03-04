package com.company.move;

import java.util.Arrays;

public class Move {
    public int[] movement ;

    public  Move(int[] movement){
        this.movement = movement;
    }

    @Override
    public String toString() {
        return String.format("Move from [%d,%d] to [%d,%d]",movement[0],movement[1],movement[2],movement[3]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return Arrays.equals(movement, move.movement);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(movement);
    }
}
