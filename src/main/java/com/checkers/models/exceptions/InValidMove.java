package com.checkers.models.exceptions;

/**
 * Class InValidMove represents an invalid move tried to be made by a player
 */
public class InValidMove extends Exception{
    public InValidMove(){
        super("Invalid Move Made");
    }

    public InValidMove(String msg){
        super(msg);
    }
}
