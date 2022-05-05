package com.checkersai;

import com.checkersai.controller.Game;
import com.checkersai.models.exceptions.InValidMove;
import com.checkersai.models.piece.PieceOwner;
import com.checkersai.models.players.AlphaBetaMinMaxAIPlayer;
import com.checkersai.models.players.RandomPlayer;

public class Main {

    public static void main(String[] args) throws InterruptedException, InValidMove, CloneNotSupportedException {

        Game game = new Game(new AlphaBetaMinMaxAIPlayer("AlphaBetaMinMax", PieceOwner.PLAYER1),new RandomPlayer("MinMax",PieceOwner.PLAYER2));
        game.play();

//        Tournament tr = new Tournament();
//        tr.playOff(1);4

    }
}