package com.company.main.controller;

import com.company.main.models.PlayerFactory;
import com.company.main.models.piece.PieceOwner;
import com.company.main.models.players.Player;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Tournament {
    Player[] players;

    public Tournament(){
        players = PlayerFactory.getAllPlayers();
    }


    public String playOff(int rounds) {
        HashMap<String,int[]> stat = new HashMap<>(); // [a,b] a is number of wins by game, b is number of win by rounds

        while(rounds>0){
            Queue<Player> playersInGame = new LinkedList<>(Arrays.asList(players));
            Player roundWinner = null;
            while(!playersInGame.isEmpty()){
                Player player1 = playersInGame.poll();
                player1.myTurn= PieceOwner.PLAYER1;

                if(playersInGame.isEmpty()){
                    roundWinner = player1;
                    break;
                }

                Player player2= playersInGame.poll();
                player2.myTurn = PieceOwner.PLAYER2;

                Player gameWinner = new Game(player1,player2).play();

                playersInGame.add(gameWinner);

                int[] result = stat.get(gameWinner.toString());

                if(result==null){
                    stat.put(gameWinner.toString(),new int[]{1,0});
                }else{
                    result[0]+=1;
                    stat.put(gameWinner.toString(),result);
                }

            }
            int[] result = stat.get(roundWinner.toString());
            result[1]+=1;
            stat.put(roundWinner.toString(),result);
            rounds-=1;
        }

        return generateReport(stat);
    }

    public String generateReport(HashMap<String,int[]> stat){
        return null;
    }

}
