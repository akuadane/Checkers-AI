package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.exceptions.CouldntConnectToServerException;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;
import com.checkers.network.*;
import com.checkers.network.Error;

import java.io.*;
import java.net.Socket;


public class RemotePlayer extends Player {
    public Socket socket;
    public ObjectInputStream reader;
    public ObjectOutputStream writer;
    public String HOST = "127.0.0.1";
    public int PORT = 6060;

    public int playerID;
    public String gameID;

    public RemotePlayer(String name, Piece.PieceOwner myTurn) throws Exception {
        super(name, myTurn);
        System.out.println("Creating Remote Player");
        this.connectToServer();
        System.out.println("Waiting for confirmation");
        acceptConfirmation();
        System.out.println("Confirmation Succeeded");
    }

    public boolean connectToServer(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            getStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    private void getStreams() throws IOException {
        System.out.println("Getting reader");
        this.reader = new ObjectInputStream(socket.getInputStream());
        System.out.println("Getting writer");
        this.writer = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Finished getting streams");
    }


    public boolean connectToServer(String host) {
        try {
            this.socket = new Socket(host, this.PORT);
            getStreams();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public void connectToServer(int port) throws Exception {
        this.socket = new Socket(this.HOST, port);
        getStreams();
    }

    public void connectToServer() throws Exception {
        System.out.println("Establishing Socket Connection");
        this.socket = new Socket(this.HOST, this.PORT);
        System.out.println("Socket Connection Established");
        getStreams();
        System.out.println("Connection established");
    }

    public void joinGame(String gameID) throws Exception {
        Action action = new JoinGame(gameID, playerID, name);
        writer.writeObject(action);
    }

    public void createGame(String id) throws Exception {
        Action action = new CreateGame(id, playerID, name);
        writer.writeObject(action);
    }

    public void acceptConfirmation() throws Exception {
        System.out.println("Waiting Confirmation");
        Action action;
        action = (Action) reader.readObject();
        while (!(action instanceof Close || action instanceof ConnectionInfo)) {
            action = (Action) reader.readObject();
        }
        if (action instanceof ConnectionInfo connectionInfo) {

            this.playerID = connectionInfo.playerID;
        }
        System.out.println("Connection Established!!");
    }

    @Override
    public Move makeMove(Board board) {
        // TODO implement this method
        return null;
    }

    public Move makeMove(Move move) {
        try {
            Action action = new MakeMove(move, this.gameID, this.playerID);
            writer.writeObject(action);
            action = (Action) reader.readObject();
            while (!(action instanceof Error || action instanceof MakeMove)) {
                action = (Action) reader.readObject();
            }
            if (action instanceof MakeMove makeMove) {
                return makeMove.move;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void waitForOpponent() throws Exception {
        System.out.println("Waiting for opponent joined confirmation");
        Action action;
        reader = new ObjectInputStream(socket.getInputStream());
        reader.reset();
        action = (Action) reader.readObject();
        System.out.println("Read action");
        while (!(action instanceof Close || action instanceof OpponentJoined)) {
            action = (Action) reader.readObject();
        }
        if (action instanceof OpponentJoined opponentJoined) {
            this.opponentName = opponentJoined.opponentName;
        } else {
            throw new CouldntConnectToServerException("Couldn't connect to opponent");
        }
        System.out.println("Waiting for opponent joined confirmation arrived");
    }
}
