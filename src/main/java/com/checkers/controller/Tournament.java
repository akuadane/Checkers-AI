package com.checkers.controller;

import com.checkers.models.PlayerFactory;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Tournament {
    Player[] players;

    public Tournament(){
        players = PlayerFactory.getAllPlayers();
    }

    //TODO add more detail in the stat
    public String playOff(int rounds) throws InValidMove, CloneNotSupportedException {
        HashMap<String,int[]> stat = new HashMap<>(); // [a,b,c] a is number of wins by game, b is number of win by rounds, loss

        while(rounds>0){
            Queue<Player> playersInGame = new LinkedList<>(Arrays.asList(players));
            Player roundWinner = null;
            while(!playersInGame.isEmpty()){
                Player player1 = playersInGame.poll();
                player1.myTurn= Piece.PieceOwner.PLAYER1;

                if(playersInGame.isEmpty()){
                    roundWinner = player1;
                    break;
                }

                Player player2= playersInGame.poll();
                player2.myTurn = Piece.PieceOwner.PLAYER2;

                Player gameWinner = new Game(player1,player2).play();
                if(gameWinner == null){

                }
                Player loser = (gameWinner.equals(player1)?player2:player1);

                playersInGame.add(gameWinner);

                int[] result = stat.get(gameWinner.toString());

                if(result==null){ // If wasn't in the hashmap before
                    stat.put(gameWinner.toString(),new int[]{0,1,0,0});
                }else{
                    result[1]+=1;
                    stat.put(gameWinner.toString(),result);
                }

                result = stat.get(loser.toString());
                if(result==null){ // If wasn't in the hashmap before
                    stat.put(loser.toString(),new int[]{0,0,1,0});
                }else{
                    result[2]+=1;
                    stat.put(loser.toString(),result);
                }


            }
            int[] result = stat.get(roundWinner.toString());
            result[0]+=1;
            stat.put(roundWinner.toString(),result);
            rounds-=1;
        }

        return generateReport(stat);
    }

    public String generateReport(HashMap<String,int[]> stat){
        String strStat="";

        for (String key :
                stat.keySet()) {
            int[] result = stat.get(key);
            strStat+= String.format(key+" championship wins: %d, wins: %d, loss: %",result[0],result[1],result[2]);
        }
        return strStat;
    }

}
