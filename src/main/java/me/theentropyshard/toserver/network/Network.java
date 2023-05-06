package me.theentropyshard.toserver.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Network extends Thread {
    private static final int PORT = 1337;

    public static long bytesSent;
    public static long bytesReceived;

    private final ServerSocket serverSocket;
    private final List<PlayerSocket> players;

    public Network() {
        this.players = new ArrayList<>();

        try {
            this.serverSocket = new ServerSocket(Network.PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.setName("Network thread");
        this.start();
    }

    @Override
    public void run() {
        try {
            while(true) {
                Socket socket = this.serverSocket.accept();
                PlayerSocket playerSocket = new PlayerSocket(socket);
                this.players.add(playerSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerSocket> getPlayers() {
        return this.players;
    }
}