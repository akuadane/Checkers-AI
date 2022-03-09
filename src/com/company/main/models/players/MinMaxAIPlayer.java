package com.company.main.models.players;

import com.company.main.models.Board;
import com.company.main.models.piece.Piece;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.move.Move;
import com.company.main.models.piece.PieceType;

public class MinMaxAIPlayer extends Player implements AIPlayer{
    MinMaxAIPlayer(String name, PieceOwner myTurn) {
        super(name,myTurn);
    }



    @Override
    public double evalBoard(Board board) {

        int myValue=0;
        int otherValue=0;

        for(int r=0;r<board.board.length;r++){
            for(int c=(1-r%2);c<board.board.length;c+=2){
                Piece piece = board.board[r][c];

                if(piece!=null){
                    if(piece.owner==myTurn){
                        if(piece.type== PieceType.PAWN)
                            myValue+=1;
                        else
                            myValue+=2;
                    }else{
                        if(piece.type== PieceType.PAWN)
                            otherValue+=1;
                        else
                            otherValue+=2;
                    }
                }
            }

        }

        if(otherValue==0)
            return Double.MAX_VALUE;

        return myValue/otherValue;
    }

    @Override
    public Move makeMove(Board board) {
        return null;
    }

    private double min(Board prevBoard){
        return 0;
    }
    private double max(Board prevBoard){
        return 0;
    }
}
