package com.company.main.models.players;

import com.company.main.models.Board;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.move.Move;

import java.util.List;
import java.util.Scanner;


public class PhysicalPlayer extends Player{

    PhysicalPlayer(String name, PieceOwner myTurn) {
        super(name,myTurn);
    }

    @Override
    public Move makeMove(Board board) {
        //TODO receive the move from the click of mouse
        // TODO remove the following implementation

        List<Move> moveList = board.findLegalMoves(myTurn);
        int index =0;
        for (Move a :
                moveList) {
            System.out.println(index + " " +a);
            index++;
        }

        System.out.print("Choice > ");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        System.out.println();

        if(i>=0 && i<moveList.size())
            return moveList.get(i);

        return null;
    }
}
