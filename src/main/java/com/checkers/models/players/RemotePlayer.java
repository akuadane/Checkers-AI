package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.exceptions.CouldntConnectToServerException;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;

import java.io.IOException;
import java.net.Socket;


public class RemotePlayer extends Player {
    public Socket socket;
    public String HOST = "127.0.0.1";
    public int PORT = 6060;

    public RemotePlayer(String name, Piece.PieceOwner myTurn) throws CouldntConnectToServerException {
        super(name, myTurn);
        if (!this.connectToServer()) {
            throw new CouldntConnectToServerException("Failed to connect to server!");
        }
    }

    public boolean connectToServer(String host, int port) {
        try {
            this.socket = new Socket(host, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean connectToServer(String host) {
        try {
            this.socket = new Socket(host, this.PORT);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean connectToServer(int port) {
        try {
            this.socket = new Socket(this.HOST, port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean connectToServer() {
        try {
            this.socket = new Socket(this.HOST, this.PORT);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    public boolean joinGame(String gameID) {
        return false;
    }

    public boolean createGame(String gameID) {

        return false;
    }

    @Override
    public Move makeMove(Board board) {
        // TODO implement this method
        return null;
    }

    public Move makeMove(Move move) {
        // TODO implement this method
        return null;
    }
}
