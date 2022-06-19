package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends Player{

    public BreadthFirstSearch(String name, Piece.PieceOwner myTurn) {
        super(name, myTurn);
    }

    public BreadthFirstSearch(){
        super("BreadFirstSearch");
    }

    @Override
    public Move makeMove(Board board) throws InValidMove, CloneNotSupportedException {
        Queue<Board> states = new LinkedList<>();
        states.add(new Board(board));

        while(states.size()>0){
            Board x = states.poll();

            for (Move move :
                    x.reachablePositionsByPlayer()) {
             Board tempBoard = new Board(x);
             tempBoard.makeMove(move);

             if(tempBoard.isGameOver()!=null) //Game is over
                    return move;

             states.add(tempBoard);

            }
        }

        return null;

    }
}
