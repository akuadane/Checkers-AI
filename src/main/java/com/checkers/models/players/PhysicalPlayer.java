package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;

import java.util.List;
import java.util.Scanner;


public class PhysicalPlayer extends Player {

    public PhysicalPlayer(String name, Piece.PieceOwner myTurn) {
        super(name,myTurn);
    }

    @Override
    public Move makeMove(Board board) {


        List<Move> moveList = board.reachablePositionsByPlayer(myTurn);
        int index =0;
        for (Move a :
                moveList) {
            index++;
        }

        while(true){
            System.out.print("Choice > ");
            Scanner scanner = new Scanner(System.in);
            int i = scanner.nextInt();

            if(i>=0 && i<moveList.size())
                return moveList.get(i);
        }

    }
}
