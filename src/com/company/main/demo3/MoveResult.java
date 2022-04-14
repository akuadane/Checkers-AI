package com.company.main.demo3;

public class MoveResult {
    private MoveType type;
    public MoveType getTYpe(){
        return type;
    }
    private Piece piece;
    public Piece getPiece(){
        return piece;
    }
    public MoveResult(MoveType type){
        this(type,null);
    }
    public MoveResult(MoveType type, Piece piece){
        this.type = type;
        this.piece = piece;
    }
}

