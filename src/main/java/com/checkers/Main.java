package com.checkers;

import com.checkers.controller.Game;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.*;


public class Main {

    public static void main(String[] args) throws InterruptedException, InValidMove, CloneNotSupportedException {

        Game game = new Game(new AlphaBetaMinMaxAIPlayer("AlphaBetaMinMax", Piece.PieceOwner.PLAYER1), new AdvancedMinMax("MinMax", Piece.PieceOwner.PLAYER2));

        game.play();

//        Tournament tr = new Tournament();
//        tr.playOff(1);

    }
}