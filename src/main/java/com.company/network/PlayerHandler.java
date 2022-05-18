package com.company.network;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerHandler extends Thread {
    public Socket playerSocket;
//    public String name;

    public PlayerHandler(Socket playerSocket) {
        System.out.println("Client Accepted!");
        this.playerSocket = playerSocket;
    }

    @Override
    public void run() {
        super.run();
        try {
            ObjectInputStream reader = new ObjectInputStream(playerSocket.getInputStream());
            reader.reset();
            ObjectOutputStream writer = new ObjectOutputStream(playerSocket.getOutputStream());
            writer.reset();
            Action action;
            while (!((action = (Action) reader.readObject()) instanceof Close)) {
                if (action instanceof CreateGame createGame) {
                    int gameID = createGame.id;
                    if (Server.games.containsKey(gameID)) {
                        writer.writeObject(new java.lang.Error("Game ID in use, please specify another ID"));
                    } else {
                        Server.games.put(gameID, new int[]{createGame.playerID, 0, createGame.playerID});
                    }
                } else if (action instanceof JoinGame joinGame) {
                    int gameID = joinGame.id;
                    if (!(Server.games.containsKey(gameID) || Server.games.get(gameID)[1] > 0)) {
                        writer.writeObject(new java.lang.Error("Game ID does not exist"));
                    } else {
                        Server.games.get(gameID)[1] = joinGame.playerID;
                    }

                } else if (action instanceof MakeMove makeMove) {
                    int gameID = makeMove.gameID;
                    if (!(Server.games.containsKey(gameID)) || Server.games.get(gameID)[2] != makeMove.playerID) {
                        writer.writeObject(new java.lang.Error("Its not your turn please wait until you peer moves"));
                    } else {
                        int[] game = Server.games.get(gameID);
                        int nextPlayer = makeMove.playerID == game[0] ? game[1] : game[0];
                        Server.games.get(gameID)[2] = nextPlayer;
                        Server.playerInfo.get(nextPlayer).writer.writeObject(makeMove);
                    }
                }
            }

        } catch (Exception e) {

        }

    }
}
