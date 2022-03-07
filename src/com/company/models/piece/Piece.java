package com.company.models.piece;

public class Piece {
    public PieceType type;
    public PieceOwner owner;

    public Piece(PieceType type, PieceOwner owner){
        this.type = type;
        this.owner= owner;
    }

   public Piece copy(){
        return new Piece(type,owner);
   }
}
