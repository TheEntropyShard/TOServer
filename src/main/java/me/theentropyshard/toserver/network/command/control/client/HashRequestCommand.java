package me.theentropyshard.toserver.network.command.control.client;

import me.theentropyshard.toserver.network.ControlChannelContext;
import me.theentropyshard.toserver.network.PlayerSocket;
import me.theentropyshard.toserver.network.command.ControlCommand;
import me.theentropyshard.toserver.network.command.IClientControlCommand;
import me.theentropyshard.toserver.network.command.control.server.HashResponseCommand;
import me.theentropyshard.toserver.network.command.control.server.OpenSpaceCommand;

import java.io.IOException;
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
        System.out.println(parameterNames);
        System.out.println(parameterValues);

        Random random = new Random();
        byte[] hash = new byte[32];
        random.nextBytes(hash);
        PlayerSocket.encoder.sendCommand(new HashResponseCommand(ByteBuffer.wrap(hash), true));
        PlayerSocket.encoder.sendCommand(new OpenSpaceCommand(0x5F691CCL));
    }

    public final ArrayList getParameterNames() {
        return this.parameterNames;
    }

    public final ArrayList getParameterValues() {
        return this.parameterValues;
    }
}
