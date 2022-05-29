package com.checkers.network;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    public static HashMap<String, int[]> games = new HashMap<>();
    public static HashMap<Integer, PlayerData> playerInfo = new HashMap<>();
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
                System.out.println("User Accepted");
                new ObjectOutputStream(socket.getOutputStream()).writeObject(new ConnectionInfo(minimumID));
                PlayerHandler playerHandler = new PlayerHandler(socket, minimumID);
                minimumID += 1;
                playerHandler.start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
