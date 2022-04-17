package com.company.main;

import com.company.main.controller.Game;
import com.company.main.controller.Tournament;
import com.company.main.models.Board;
import com.company.main.models.exceptions.InValidMove;
import com.company.main.models.move.Move;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.players.AlphaBetaMinMaxAIPlayer;
import com.company.main.models.players.MinMaxAIPlayer;
import com.company.main.models.players.PhysicalPlayer;
import com.company.main.models.players.RandomPlayer;

import java.time.LocalTime;

public class Main {

    public static void main(String[] args) throws InterruptedException, InValidMove, CloneNotSupportedException {

        Game game = new Game(new AlphaBetaMinMaxAIPlayer("AlphaBetaMinMax", PieceOwner.PLAYER1),new RandomPlayer("MinMax",PieceOwner.PLAYER2));
        game.play();

//        Tournament tr = new Tournament();
//        tr.playOff(1);

    }
}