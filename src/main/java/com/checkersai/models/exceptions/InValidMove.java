package com.checkersai.models.exceptions;

public class InValidMove extends Exception{
    public InValidMove(){
        super("Invalid Move Made");
    }

    public InValidMove(String msg){
        super(msg);
    }
}
