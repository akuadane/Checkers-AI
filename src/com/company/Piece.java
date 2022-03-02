package com.company;

enum PieceType{KING, PAWN}
enum PieceOwner{PLAYER1,PLAYER2}

public class Piece {
    PieceType type;
    PieceOwner owner;

    Piece(PieceType type, PieceOwner owner){
        this.type = type;
        this.owner= owner;
    }
}
