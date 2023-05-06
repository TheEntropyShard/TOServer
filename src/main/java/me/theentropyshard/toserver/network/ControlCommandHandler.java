package me.theentropyshard.toserver.network;

import me.theentropyshard.toserver.network.command.IClientControlCommand;
import me.theentropyshard.toserver.network.protocol.CommandHandler;
import me.theentropyshard.toserver.network.protocol.impl.Protocol;

public class ControlCommandHandler implements CommandHandler {
    private ControlChannelContext context = new ControlChannelContext();

    public ControlCommandHandler() {
        this.context.setSpaceProtocol(Protocol.INSTANCE);
    }

    @Override
    public void executeCommand(Object o) {
        if(o instanceof IClientControlCommand) {
            ((IClientControlCommand) o).execute(this.context);
        }
    }

    @Override
    public void onConnectionOpen() {
        System.out.println("Client connected");
    }

    @Override
    public void onConnectionClose() {
        System.out.println("Client disconnected");
    }
}
