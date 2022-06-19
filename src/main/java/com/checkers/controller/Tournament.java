package com.checkers.controller;

import com.checkers.models.PlayerFactory;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.piece.Piece;
import com.checkers.models.players.Player;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Tournament {
    Player[] players;
    final int GAME_WINS = 0;
    final int ROUND_WINS = 1;
    final int GAME_LOSSES = 2;
    final int GAME_DRAWS = 3;
    HashMap<String,int[]> stat = new HashMap<>(); // [a,b,c,d] a is number of wins by game, b is number of win by rounds, c for loss, and d draws
    public Tournament(){
        players = PlayerFactory.getAllPlayers();
    }

    //TODO add more detail in the stat
    public String playOff(int rounds) throws InValidMove, CloneNotSupportedException {
        Thread[] threads = new Thread[rounds];
        int gamesPlayed = 0;
        while(rounds>0){
            List playerList = Arrays.asList(PlayerFactory.getAllPlayers());
            Collections.shuffle(playerList);
            Queue<Player> playersInGame = new LinkedList<>(playerList);

            Champsionship champ = new Champsionship(playersInGame);
            Thread newThread =  new Thread(champ);
            threads[rounds-1] = newThread;
            newThread.start();
            rounds-=1;
        }

        for (Thread t :
                threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        dumpResultToCSV(stat);
        return generateReport(stat);
    }

    public String generateReport(HashMap<String,int[]> stat){
        String strStat="";

        for (String key :
                stat.keySet()) {
            int[] result = stat.get(key);
            strStat+= String.format(key+" championship wins: %d, Game wins: %d, loss: %d, draws: %d\n",result[ROUND_WINS],result[GAME_WINS],result[GAME_LOSSES], result[GAME_DRAWS]);
        }
        return strStat;
    }

    private void dumpResultToCSV(HashMap<String,int[]> stat) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileOutputStream("tournament_result.csv"));
            writer.println("player,championship wins,total wins,total losses, total draws");
            for (String key :
                    stat.keySet()) {
                int[] result = stat.get(key);
                String playerStat = String.format(key + ", %d, %d, %d,%d", result[ROUND_WINS], result[GAME_WINS], result[GAME_LOSSES], result[GAME_DRAWS]);
                writer.println(playerStat);
            }

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } finally {
            writer.close();
        }

    }

    class Champsionship implements Runnable {
        private Queue<Player> playersInGame;

        Champsionship(Queue<Player> playersInGame){
            this.playersInGame = playersInGame;
        }
        @Override
        public void run() {
            Player roundWinner=null;
            while(!playersInGame.isEmpty()){
                Player player1 = playersInGame.poll();
                player1.myTurn= Piece.PieceOwner.PLAYER1;

                int winnerResultIndex = 0;
                int loserResultIndex  = 0;

                if(playersInGame.isEmpty()){
                    roundWinner = player1;
                    break;
                }
                Player player2= playersInGame.poll();
                player2.myTurn = Piece.PieceOwner.PLAYER2;

                int[] result = stat.get(player1.toString());

                if(result==null) { // If wasn't in the hashmap before
                    stat.put(player1.toString(), new int[]{0, 0, 0, 0});
                }
                result = stat.get(player2.toString());
                if(result==null){ // If wasn't in the hashmap before
                    stat.put(player2.toString(),new int[]{0,0,0,0});
                }

                System.out.println(String.format("%s Vs %s",player1,player2));
                Player gameWinner = null;
                try {
                    gameWinner = new Game(player1,player2).playWithoutDebugging();
                } catch (InValidMove e) {
                    throw new RuntimeException(e);
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                Player loser;
                if(gameWinner == null){
                    gameWinner = player1;
                    loser = player2;

                    winnerResultIndex = GAME_DRAWS;
                    loserResultIndex = GAME_DRAWS;

                    playersInGame.add(gameWinner);
                    //playersInGame.add(loser);
                    System.out.println("Result: DRAW");
                }
                else {
                    loser = (gameWinner.equals(player1) ? player2 : player1);
                    winnerResultIndex = GAME_WINS;
                    loserResultIndex = GAME_LOSSES;
                    playersInGame.add(gameWinner);
                    System.out.println(String.format("Result: %s won.",gameWinner));
                }

                result = stat.get(gameWinner.toString());
                result[winnerResultIndex]++;

                result = stat.get(loser.toString());
                result[loserResultIndex]++;

            }
            int[] result = stat.get(roundWinner.toString());
            result[ROUND_WINS]+=1;
        }
    }


}

