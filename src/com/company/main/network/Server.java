package com.company.main.network;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    public static HashMap<Integer, int[]> games;
    public static HashMap<Integer, PlayerData> playerInfo;
    public static int minimumID = 1;
    static int PORT = 6060;

    public static void main(String[] args) {
        if (args.length >= 1) PORT = Integer.parseInt(args[0]);
        listenSocket();
    }

    static void listenSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server listening on port: " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                PlayerHandler playerHandler = new PlayerHandler(socket);
                Action action = new ConnectionInfo(minimumID);
                minimumID += 1;
                new ObjectOutputStream(socket.getOutputStream()).writeObject(action);
                playerHandler.start();

            }
        } catch (Exception e) {

        }
    }
}
