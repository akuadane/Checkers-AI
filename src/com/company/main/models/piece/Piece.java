package com.company.main.models.piece;

public class Piece {
    public PieceType type;
    public PieceOwner owner;

    public Piece(PieceType type, PieceOwner owner){
        this.type = type;
        this.owner= owner;
    }

    @Override
    public Piece clone(){
        return new Piece(type,owner);
    }
}
