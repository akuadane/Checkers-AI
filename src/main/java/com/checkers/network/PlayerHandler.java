package com.checkers.network;

/*
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerHandler extends Thread {
    public Socket playerSocket;
    public ObjectOutputStream writer;
    public ObjectInputStream reader;
    public int playerID;
//    public String name;

    public PlayerHandler(Socket playerSocket, int playerID) throws IOException {
        this.playerSocket = playerSocket;
        this.playerID = playerID;
        reader = new ObjectInputStream(playerSocket.getInputStream());
        writer = new ObjectOutputStream(playerSocket.getOutputStream());
        Server.playerInfo.put(playerID, new PlayerData(writer, reader));
    }

    @Override
    public void run() {
        try {
            Action action;
            System.out.println("Waiting user actions");
            while (!((action = (Action) reader.readObject()) instanceof Close)) {
                if (action instanceof CreateGame createGame) {
                    System.out.println("Create Game Action");
                    String gameID = createGame.id;
                    if (Server.games.containsKey(gameID)) {
                        writer.writeObject(new Error("Game ID in use, please specify another ID"));
                    } else {
                        Server.games.put(gameID, new int[]{createGame.playerID, 0, createGame.playerID});
                        System.out.println("Game create Successful");
                    }
                } else if (action instanceof JoinGame joinGame) {
                    System.out.println("Join Game Action");
                    String gameID = joinGame.id;
                    if (!(Server.games.containsKey(gameID) || Server.games.get(gameID)[1] > 0)) {
                        writer.writeObject(new Error("Game ID does not exist"));
                    } else {
                        System.out.println("Joining Opponents");
                        Server.games.get(gameID)[1] = joinGame.playerID;
                        int pl1ID = Server.games.get(gameID)[0];
                        sleep(1000);
                        ObjectOutputStream opponentWriter = Server.playerInfo.get(pl1ID).writer;

                        opponentWriter.writeObject(new OpponentJoined(joinGame.name));
                        writer.writeObject(new OpponentJoined(Server.playerInfo.get(pl1ID).name));

                        System.out.println("Opponents JOINED");
                    }

                } else if (action instanceof MakeMove makeMove) {
                    String gameID = makeMove.gameID;
                    if (!(Server.games.containsKey(gameID)) || Server.games.get(gameID)[2] != makeMove.playerID) {
                        writer.writeObject(new java.lang.Error("Its not your turn please wait until you peer moves"));
                    } else {
                        int[] game = Server.games.get(gameID);
                        int nextPlayer = makeMove.playerID == game[0] ? game[1] : game[0];
                        Server.games.get(gameID)[2] = nextPlayer;
                        Server.playerInfo.get(nextPlayer).writer.writeObject(makeMove);
                    }
                }
                writer.flush();
            }

        } catch (Exception e) {

        }

    }
}
*/