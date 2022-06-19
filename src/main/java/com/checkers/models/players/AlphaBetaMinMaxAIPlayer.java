package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Pawn;
import com.checkers.models.piece.Piece;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;


public class AlphaBetaMinMaxAIPlayer extends Player implements AIPlayer {
    LocalTime stTime;
    final int MAX_SECONDS = 5;
     int MAX_DEPTH=10;

    public AlphaBetaMinMaxAIPlayer(String name, Piece.PieceOwner myTurn) {
        super(name,myTurn);
    }

    public AlphaBetaMinMaxAIPlayer(){
        super("AlphaBeta", Piece.PieceOwner.PLAYER2);
    }
    public AlphaBetaMinMaxAIPlayer(String name, Piece.PieceOwner myTurn,int depth){
        this(name,myTurn);
        MAX_DEPTH = depth;
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


        for (Move mv :
                board.reachablePositionsByPlayer(myTurn)) {

            Board temp = new Board(board);
            temp.makeMove(mv);
            double moveVal  = min(temp,MAX_DEPTH,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY);

            if(max<moveVal || myMove==null){
                myMove = mv;
                max=moveVal;
            }
        }

        return myMove;
    }

    private double min(Board prevBoard, int depth, double alpha, double beta) throws InValidMove, CloneNotSupportedException {
        if(prevBoard.isGameOver()!=null)
            return evalBoard(prevBoard);
        if(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==-1)
            return evalBoard(prevBoard);

        if(depth==0)
            return evalBoard(prevBoard);

        List<Move> moveList = prevBoard.reachablePositionsByPlayer();

        Collections.shuffle(moveList);
        double min=Double.MAX_VALUE;

        for (Move mv :
                moveList) {
            Board temp = new Board(prevBoard);
            temp.makeMove(mv);

            double moveVal = max(temp,depth-1,alpha,beta);
            min = Math.min(min,moveVal);
            beta = Math.min(beta, moveVal);


            if(beta <= alpha){
                break;
            }

        }
        return min;
    }

    private double max(Board prevBoard,  int depth, double alpha, double beta) throws InValidMove, CloneNotSupportedException {
        if(prevBoard.isGameOver()!=null)
            return evalBoard(prevBoard);
        if(stTime.plusSeconds(MAX_SECONDS).compareTo(LocalTime.now())==-1)
            return evalBoard(prevBoard);
        if(depth==0)
            return evalBoard(prevBoard);

        List<Move> moveList = prevBoard.reachablePositionsByPlayer();


        Collections.shuffle(moveList);
        double max=Double.MIN_VALUE;

        for (Move mv :
                moveList) {
            Board temp = new Board(prevBoard);
            temp.makeMove(mv);

            double moveVal = min(temp,depth-1,alpha,beta);
            max = Math.max(max,moveVal);
            alpha = Math.max(alpha,moveVal);


            if(beta <= alpha){
                break;
            }
        }
        return max;

    }

}
