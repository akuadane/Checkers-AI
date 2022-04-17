package com.company.main.models.players;

import com.company.main.models.Board;
import com.company.main.models.move.Move;
import com.company.main.models.piece.PieceOwner;
import com.company.main.network.Error;
import com.company.main.network.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RemotePlayer extends Player {
    public int playerID, gameID;
    public String name;
    public String serverAddress = "127.0.0.1";
    public int serverPort = 6060;
    public ObjectOutputStream writer;
    public ObjectInputStream reader;
    public int[] move;
    public boolean moveMade = false;

    RemotePlayer(String name, PieceOwner myTurn) {
        super(name, myTurn);
        this.connectToServer();
    }

    @Override
    public Move makeMove(Board board) {
        // TODO implement this method
        while (!this.moveMade) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new Move(move);
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            this.reader = new ObjectInputStream(socket.getInputStream());
            this.writer = new ObjectOutputStream(socket.getOutputStream());
            IncomingReader reader = new IncomingReader();
            reader.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createGame(int gameID) throws IOException {
        Action action = new CreateGame(gameID, playerID, name);
        writer.writeObject(action);
    }

    public void joinGame(int gameID) throws IOException {
        Action action = new JoinGame(gameID, playerID, name);
        writer.writeObject(action);
    }

    private class IncomingReader extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Action message;
                message = (Action) reader.readObject();
                System.out.println(message);
                System.out.println(((ConnectionInfo) message).playerID);
                while (!(message instanceof Close)) {
                    if (message instanceof ConnectionInfo connectionInfo) {
                        playerID = connectionInfo.playerID;
                    } else if (message instanceof MakeMove makeMove) {
                        move[0] = makeMove.x1;
                        move[1] = makeMove.y1;
                        move[2] = makeMove.x2;
                        move[3] = makeMove.y2;
                        moveMade = true;

                    } else if (message instanceof Error error) {
                        System.out.println(error.message);
                    }
                    message = (Action) reader.readObject();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        new RemotePlayer("Miruts", PieceOwner.PLAYER1);
    }
}


