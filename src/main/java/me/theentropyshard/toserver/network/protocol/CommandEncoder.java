package me.theentropyshard.toserver.network.protocol;

import me.theentropyshard.toserver.network.command.IConnectionInitCommand;
import me.theentropyshard.toserver.network.command.control.client.HashRequestCommand;
import me.theentropyshard.toserver.network.protocol.impl.Protocol;
import me.theentropyshard.toserver.network.protocol.protection.IProtectionContext;
import me.theentropyshard.toserver.network.protocol.protection.PrimitiveProtectionContext;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class CommandEncoder {
    private final ICodec commandCodec;
    private final CommandHandler commandHandler;
    private final IProtectionContext protectionContext;
    private final Queue commandsToServer;

    public CommandEncoder(ICodec commandCodec, CommandHandler commandHandler, IProtectionContext protectionContext) {
        this.commandCodec = commandCodec;
        this.commandHandler = commandHandler;
        this.protectionContext = protectionContext;
        this.commandsToServer = new ArrayBlockingQueue(1000);

        this.commandCodec.init(Protocol.INSTANCE);
    }

    public void decodeAndExecuteCommandsFromServer(ByteBuffer data) {
        // Decrypt data
        ByteBuffer decryptedData = ByteBuffer.allocate(16384);
        this.protectionContext.decrypt(data, decryptedData);

        // Unwrap packet
        ProtocolBuffer protocolBuffer = new ProtocolBuffer(ByteBuffer.allocate(16384), new OptionalMap());
        boolean b = Protocol.INSTANCE.unwrapPacket(decryptedData, protocolBuffer);
        if(b) {
            System.out.println("Unwrapped packet");
        } else {
            System.out.println("Could not unwrap packet");
        }

        // Decode command
        Object decodedCommand = this.commandCodec.decode(protocolBuffer);

        // Execute command
        this.commandHandler.executeCommand(decodedCommand);
    }

    public void sendCommand(Object command) {
        System.out.println(command);
        boolean var2 = command instanceof IConnectionInitCommand;
        ProtocolBuffer buffer = new ProtocolBuffer(ByteBuffer.allocate(4096), new OptionalMap());
        this.commandCodec.encode(buffer, command);
        buffer.flip();
        ByteBuffer var3 = ByteBuffer.allocate(4096);
        Protocol.INSTANCE.wrapPacket(var3, buffer);
        buffer.clear();
        ByteBuffer var5 = ByteBuffer.allocate(4096);
        IProtectionContext protectionContext;
        if(var2) {
            protectionContext = PrimitiveProtectionContext.INSTANCE;
        } else {
            protectionContext = this.protectionContext;
        }

        protectionContext.encrypt(var3, var5);
        var3.clear();
        var5.flip();
        this.commandsToServer.add(var5);
    }

    public Queue getCommandsToServer() {
        return this.commandsToServer;
    }
}
