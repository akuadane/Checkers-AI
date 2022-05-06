package com.company.models.players;

import com.company.models.Board;
import com.company.models.exceptions.InValidMove;
import com.company.models.move.Move;
import com.company.models.piece.Pawn;
import com.company.models.piece.Piece;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;


public class AlphaBetaMinMaxAIPlayer extends Player implements AIPlayer {
    LocalTime stTime;
    final int MAX_SECONDS = 5;
    final int MAX_DEPTH=50;

    public AlphaBetaMinMaxAIPlayer(String name, Piece.PieceOwner myTurn) {
        super(name,myTurn);
    }

    public AlphaBetaMinMaxAIPlayer(){
        super("AlphaBeta", Piece.PieceOwner.PLAYER2);
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
                        if(piece instanceof Pawn)
                            myValue+=1;
                        else
                            myValue+=2;
                    }else{
                        if(piece instanceof Pawn)
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
    public Move makeMove(Board board) throws InValidMove, CloneNotSupportedException {
        stTime = LocalTime.now();
        double max=Double.MIN_VALUE;
        Move myMove=null ;
        Piece.PieceOwner nextInTurn = (myTurn== Piece.PieceOwner.PLAYER1)? Piece.PieceOwner.PLAYER2: Piece.PieceOwner.PLAYER1;


        for (Move mv :
                board.reachablePositionsByPlayer(myTurn)) {

            Board temp = new Board(board);
            temp.makeMove(mv);
            double moveVal  = min(temp,nextInTurn,MAX_DEPTH,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);

            if(max<moveVal || myMove==null){
                myMove = mv;
                max=moveVal;
            }
        }

        return myMove;
    }

    private double min(Board prevBoard, Piece.PieceOwner inTurn, int depth, double alpha, double beta) throws InValidMove, CloneNotSupportedException {
        if(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==-1)
            return evalBoard(prevBoard);

        if(depth==0)
            return evalBoard(prevBoard);

        List<Move> moveList = prevBoard.reachablePositionsByPlayer(inTurn);
        Piece.PieceOwner nextInTurn = (inTurn== Piece.PieceOwner.PLAYER1)? Piece.PieceOwner.PLAYER2: Piece.PieceOwner.PLAYER1;

        if(moveList.size()==0)
            return evalBoard(prevBoard);

        Collections.shuffle(moveList);
        double min=Double.MAX_VALUE;

        for (Move mv :
                moveList) {
            Board temp = new Board(prevBoard);
            temp.makeMove(mv);

            double moveVal = max(temp,nextInTurn,depth-1,alpha,beta);
            min = Math.min(min,moveVal);
            beta = Math.min(beta, moveVal);


            if(beta <= alpha){
                break;
            }

        }
        return min;
    }

    private double max(Board prevBoard, Piece.PieceOwner inTurn, int depth, double alpha, double beta) throws InValidMove, CloneNotSupportedException {

        if(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==-1)
            return evalBoard(prevBoard);
        if(depth==0)
            return evalBoard(prevBoard);

        List<Move> moveList = prevBoard.reachablePositionsByPlayer(inTurn);
        Piece.PieceOwner nextInTurn = (inTurn== Piece.PieceOwner.PLAYER1)? Piece.PieceOwner.PLAYER2: Piece.PieceOwner.PLAYER1;

        if(moveList.size()==0)
            return evalBoard(prevBoard);
        Collections.shuffle(moveList);
        double max=Double.MIN_VALUE;

        for (Move mv :
                moveList) {
            Board temp = new Board(prevBoard);
            temp.makeMove(mv);

            double moveVal = min(temp,nextInTurn,depth-1,alpha,beta);
            max = Math.max(max,moveVal);
            alpha = Math.max(alpha,moveVal);


            if(beta <= alpha){
                break;
            }
        }
        return max;

    }

}
