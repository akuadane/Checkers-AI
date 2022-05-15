package com.company;

import com.company.controller.Game;
import com.company.models.exceptions.InValidMove;
import com.company.models.piece.Piece;
import com.company.models.players.AlphaBetaMinMaxAIPlayer;
import com.company.models.players.RandomPlayer;

public class Main {

    public static void main(String[] args) throws InterruptedException, InValidMove, CloneNotSupportedException {

        Game game = new Game(new AlphaBetaMinMaxAIPlayer("AlphaBetaMinMax", Piece.PieceOwner.PLAYER1),new RandomPlayer("MinMax", Piece.PieceOwner.PLAYER2));
        game.play();

//        Tournament tr = new Tournament();
//        tr.playOff(1);

    }
}