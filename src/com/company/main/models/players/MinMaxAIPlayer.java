package com.company.main.models.players;
import com.company.main.models.Board;
import com.company.main.models.piece.Piece;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.move.Move;
import com.company.main.models.piece.PieceType;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;


public class MinMaxAIPlayer extends Player implements AIPlayer{

    LocalTime stTime;
    final int MAX_SECONDS = 3;
    final int MAX_DEPTH=20;

    public MinMaxAIPlayer(String name, PieceOwner myTurn) {
        super(name,myTurn);
    }
    public MinMaxAIPlayer(){
        super("MinMaxAIPlayer");
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
        stTime = LocalTime.now();
        double max=Double.MIN_VALUE;
        Move myMove=null ;
        PieceOwner nextInTurn = (myTurn==PieceOwner.PLAYER1)? PieceOwner.PLAYER2: PieceOwner.PLAYER1;
        System.out.println("choosing the best move");

       for (Move mv :
                board.findLegalMoves(myTurn)) {

            Board temp = board.clone();
            temp.makeMove(mv,myTurn);
            double moveVal  = min(temp,nextInTurn,MAX_DEPTH);

            if(max<moveVal || myMove==null){
                myMove = mv;
                max=moveVal;
            }
        }

        return myMove;
    }

    private double min(Board prevBoard,PieceOwner inTurn,int depth){
        if(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==-1)
            return evalBoard(prevBoard);

        if(depth==0)
            return evalBoard(prevBoard);

        List<Move> moveList = prevBoard.findLegalMoves(inTurn);
        PieceOwner nextInTurn = (inTurn==PieceOwner.PLAYER1)? PieceOwner.PLAYER2: PieceOwner.PLAYER1;

        if(moveList.size()==0)
            return evalBoard(prevBoard);

        Collections.shuffle(moveList);
        double min=Double.MAX_VALUE;

        for (Move mv :
                moveList) {
            Board temp = prevBoard.clone();
            temp.makeMove(mv,inTurn);

            double moveVal = max(temp,nextInTurn,depth-1);
            min = Math.min(min,moveVal);

        }

        return min;
    }
    private double max(Board prevBoard,PieceOwner inTurn,int depth){

        if(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==-1)
            return evalBoard(prevBoard);
        if(depth==0)
            return evalBoard(prevBoard);

        List<Move> moveList = prevBoard.findLegalMoves(inTurn);
        PieceOwner nextInTurn = (inTurn==PieceOwner.PLAYER1)? PieceOwner.PLAYER2: PieceOwner.PLAYER1;

        if(moveList.size()==0)
            return evalBoard(prevBoard);
        Collections.shuffle(moveList);
        double max=Double.MIN_VALUE;

        for (Move mv :
                moveList) {
            Board temp = prevBoard.clone();
            temp.makeMove(mv,inTurn);

            double moveVal = min(temp,nextInTurn,depth-1);
            max = Math.max(max,moveVal);

        }
        return max;

    }
}
