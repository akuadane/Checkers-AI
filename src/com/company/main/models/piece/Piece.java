package com.company.main.models.piece;

import javax.xml.validation.TypeInfoProvider;

public class Piece {
    public PieceType type;
    public PieceOwner owner;

    public Piece(PieceType type, PieceOwner owner){
        this.type = type;
        this.owner= owner;
    }


    public Piece clone(){
        return new Piece(type,owner);
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;

        if(!(obj instanceof Piece))
            return false;

        Piece p = (Piece) obj;
        return (this.owner.equals(p.owner) && this.type.equals(p.type));
    }

    @Override
    public String toString() {
        return type.toString() + " of " +owner.toString();
    }
}
