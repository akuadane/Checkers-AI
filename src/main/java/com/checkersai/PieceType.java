package com.checkersai;

public enum PieceType {
    //we set moveDir to 1 for Red since it goes down and -1 for black cause it moves in opposite direction with Red
    RED(1), Black(-1);
    final int moveDir;
    PieceType(int moveDir){
        this.moveDir = moveDir;
    }

}
