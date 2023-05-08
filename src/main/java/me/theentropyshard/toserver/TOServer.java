package me.theentropyshard.toserver;

import me.theentropyshard.toserver.network.Network;
import me.theentropyshard.toserver.network.PlayerSocket;

import java.util.List;

public final class TOServer {

    private final Network network;
    private final Thread mainThread;

    public TOServer() {
        this.network = new Network();
        this.mainThread = Thread.currentThread();

        this.mainThread.setName("Main thread");

        this.networkCycle();
    }

    private void networkCycle() {
        while(true) {
            List<PlayerSocket> players = this.network.getPlayers();
            for(PlayerSocket player : players) {
                if(player != null) {
                    System.out.println("{}{}{}{}{}{ Processing player " + player);
                    player.processNetwork();
                    System.out.println("{}{}{}{}{}{ Processed player " + player);
                } else {
                    System.out.println("wtf");
                }
            }
        }
    }
}
