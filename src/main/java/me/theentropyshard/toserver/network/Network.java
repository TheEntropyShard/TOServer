package me.theentropyshard.toserver.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Network extends Thread {
    private static final int PORT = 1337;

    public static long bytesSent;
    public static long bytesReceived;

    private final ServerSocket serverSocket;
    private final List<PlayerSocket> players;

    public Network() {
        this.players = new CopyOnWriteArrayList<>();

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
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("Connection accepted: " + socket.getRemoteSocketAddress());


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
