package me.theentropyshard.toserver.network.command.control.client;

import me.theentropyshard.toserver.network.ControlChannelContext;
import me.theentropyshard.toserver.network.PlayerSocket;
import me.theentropyshard.toserver.network.command.ControlCommand;
import me.theentropyshard.toserver.network.command.IClientControlCommand;
import me.theentropyshard.toserver.network.command.control.server.HashResponseCommand;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

public class HashRequestCommand extends ControlCommand implements IClientControlCommand {
    private final ArrayList parameterNames;
    private final ArrayList parameterValues;

    public HashRequestCommand(ArrayList parameterNames, ArrayList parameterValues) {
        super(ControlCommand.CL_HASH_REQUEST, "Hash request");
        this.parameterNames = parameterNames;
        this.parameterValues = parameterValues;
    }

    @Override
    public void execute(ControlChannelContext context) {
        Random random = new Random();
        byte[] hash = new byte[32];
        random.nextBytes(hash);
        System.out.println("CALLED");
        PlayerSocket.encoder.sendCommand(new HashResponseCommand(ByteBuffer.wrap(hash), true));
    }

    public final ArrayList getParameterNames() {
        return this.parameterNames;
    }

    public final ArrayList getParameterValues() {
        return this.parameterValues;
    }
}
