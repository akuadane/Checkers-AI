package com.checkers.models.players;

import com.checkers.models.Board;
import com.checkers.models.exceptions.InValidMove;
import com.checkers.models.move.Move;
import com.checkers.models.piece.Piece;
import com.checkers.network.*;
import com.checkers.network.Error;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.function.Function;


public class RemotePlayer extends Player {
    public String opponentName;
    private static final String LOCALHOST = "127.0.0.1";
    public static final int PORT = 6060;
    Socket mySocket;
    ServerSocket myServerSocket;
    ObjectOutputStream writer;
    ObjectInputStream reader;
    CompletableFuture<Move> makeMoveFuture;

    public CompletableFuture<Void> getWaitOpponentFuture() {
        return waitOpponentFuture;
    }

    public void setWaitOpponentFuture(CompletableFuture<Void> waitOpponentFuture) {
        this.waitOpponentFuture = waitOpponentFuture;
    }

    CompletableFuture<Void> waitOpponentFuture;
    Function<Void, Void> onErrorCallback;
    Function<Void, Void> onCloseCallback;
    Function<Move, Void> onMakeMoveCallbackFunc;

    public RemotePlayer(String name, Piece.PieceOwner myTurn) throws Exception {
        super(name, myTurn);
        if (myTurn == Piece.PieceOwner.PLAYER1) {
            if (!this.host().get()) throw new Exception("Couldn't Host");
        } else {
            if (!this.join().get()) throw new Exception("Couldn't Join Game");
        }
        CommunicationService communicationService = new CommunicationService();
        communicationService.start();
    }

    public RemotePlayer(String name, Piece.PieceOwner myTurn, String hostname, int port) throws Exception {
        super(name, myTurn);
        if (myTurn == Piece.PieceOwner.PLAYER1) {
            if (!this.host(port).get()) throw new Exception("Couldn't Host");
        } else {
            if (!this.join(hostname, port).get()) throw new Exception("Couldn't Join Game");
        }
        CommunicationService communicationService = new CommunicationService();
        communicationService.start();
    }

    public Future<Boolean> join(String host, int port) {
        Callable<Boolean> joinCallable = () -> {
            try {
                mySocket = new Socket(host, port);
                writer = new ObjectOutputStream(mySocket.getOutputStream());
                reader = new ObjectInputStream(mySocket.getInputStream());
                writer.writeObject(new ConnectionInfo(name));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return executorService.submit(joinCallable);
    }

    public Future<Boolean> join(String host) {
        return join(host, PORT);
    }

    public Future<Boolean> join(int port) {
        return join(LOCALHOST, port);
    }

    public Future<Boolean> join() {
        return join(LOCALHOST, PORT);
    }

    public Future<Boolean> host(int port) {
        Callable<Boolean> hostTask = () -> {
            try {
                myServerSocket = new ServerSocket(port);
                mySocket = myServerSocket.accept();
                writer = new ObjectOutputStream(mySocket.getOutputStream());
                reader = new ObjectInputStream(mySocket.getInputStream());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return executorService.submit(hostTask);
    }

    public Future<Boolean> host() {
        return host(PORT);
    }


    @Override
    public Move makeMove(Board board) throws InValidMove, CloneNotSupportedException {
        return null;
    }

    @Override
    public boolean makeMove(Move move) throws InValidMove {
        Action action = new MakeMove(move);
        try {
            writer.writeObject(action);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private class CommunicationService extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Action action;
                while (!((action = (Action) reader.readObject()) instanceof Close)) {
                    if (action instanceof MakeMove makeMove)
                        onMakeMoveCallback(makeMove);
                    else if (action instanceof ConnectionInfo connectionInfo)
                        onConnectionInfoCallback(connectionInfo);
                    else if (action instanceof Error error)
                        onErrorCallback(error);
                }
                Close close = (Close) action;
                onCloseCallback(close);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void onErrorCallback(Error error) {

    }

    public void onConnectionInfoCallback(ConnectionInfo action) throws IOException {
        if (myTurn == Piece.PieceOwner.PLAYER1) {
            writer.writeObject(new ConnectionInfo(name));
        }
        opponentName = action.playerName;
        this.getWaitOpponentFuture().complete(null);
    }

    public void onMakeMoveCallback(MakeMove action) {
        this.onMakeMoveCallbackFunc.apply(action.move);
    }

    public void onCloseCallback(Close action) {
    }

    public boolean writeAction(Action action) {
        try {
            writer.writeObject(action);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public Socket getMySocket() {
        return mySocket;
    }

    public void setMySocket(Socket mySocket) {
        this.mySocket = mySocket;
    }

    public ServerSocket getMyServerSocket() {
        return myServerSocket;
    }

    public void setMyServerSocket(ServerSocket myServerSocket) {
        this.myServerSocket = myServerSocket;
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public void setWriter(ObjectOutputStream writer) {
        this.writer = writer;
    }

    public ObjectInputStream getReader() {
        return reader;
    }

    public void setReader(ObjectInputStream reader) {
        this.reader = reader;
    }

    public CompletableFuture<Move> getMakeMoveFuture() {
        return makeMoveFuture;
    }

    public void setMakeMoveFuture(CompletableFuture<Move> makeMoveFuture) {
        this.makeMoveFuture = makeMoveFuture;
    }

    public Function<Void, Void> getOnErrorCallback() {
        return onErrorCallback;
    }

    public void setOnErrorCallback(Function<Void, Void> onErrorCallback) {
        this.onErrorCallback = onErrorCallback;
    }

    public Function<Void, Void> getOnCloseCallback() {
        return onCloseCallback;
    }

    public void setOnCloseCallback(Function<Void, Void> onCloseCallback) {
        this.onCloseCallback = onCloseCallback;
    }

    public Function<Move, Void> getOnMakeMoveCallbackFunc() {
        return onMakeMoveCallbackFunc;
    }

    public void setOnMakeMoveCallbackFunc(Function<Move, Void> onMakeMoveCallbackFunc) {
        this.onMakeMoveCallbackFunc = onMakeMoveCallbackFunc;
    }
}
