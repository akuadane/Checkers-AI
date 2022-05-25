package com.company;

import com.company.controller.Game;
import com.company.models.exceptions.InValidMove;
import com.company.models.piece.Piece;
import com.company.models.players.AlphaBetaMinMaxAIPlayer;
import com.company.models.players.BackRowAIPlayer;
import com.company.models.players.MinMaxAIPlayer;
import com.company.models.players.RandomPlayer;
import com.company.models.players.mcts.MCTSPlayer;

public class Main {

    public static void main(String[] args) throws InterruptedException, InValidMove, CloneNotSupportedException {

        Game game = new Game(new AlphaBetaMinMaxAIPlayer("AlphaBetaMinMax", Piece.PieceOwner.PLAYER1),new MCTSPlayer("MCTS", Piece.PieceOwner.PLAYER2));
        game.play();

//        Tournament tr = new Tournament();
//        tr.playOff(1);

    }
}