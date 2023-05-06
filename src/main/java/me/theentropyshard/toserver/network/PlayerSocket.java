package me.theentropyshard.toserver.network;

import me.theentropyshard.toserver.network.protocol.CommandEncoder;
import me.theentropyshard.toserver.network.protocol.CommandHandler;
import me.theentropyshard.toserver.network.protocol.codec.ControlRootCodec;
import me.theentropyshard.toserver.network.protocol.protection.PrimitiveProtectionContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Queue;

public class PlayerSocket {
    private final Socket socket;

    public static CommandEncoder encoder;

    private CommandHandler commandHandler;
    private CommandEncoder commandEncoder;

    public PlayerSocket(Socket socket) {
        this.socket = socket;
        this.commandHandler = new ControlCommandHandler();
        encoder = this.commandEncoder = new CommandEncoder(new ControlRootCodec(), this.commandHandler, PrimitiveProtectionContext.INSTANCE);
    }

    public void processNetwork() {
        this.receiveData();
        this.sendData();
    }

    private void receiveData() {
        try {
            byte[] buffer = new byte[4096];
            ByteBuffer data = ByteBuffer.allocate(4096);

            InputStream is = this.socket.getInputStream();
            int nRead = is.read(buffer);

            data.put(buffer, 0, nRead);
            data.flip();

            this.commandEncoder.decodeAndExecuteCommandsFromServer(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData() {
        try {
            OutputStream os = this.socket.getOutputStream();
            Queue commandsToServer = this.commandEncoder.getCommandsToServer();

            for(Object command : commandsToServer) {
                ByteBuffer data = (ByteBuffer) command;
                os.write(data.array());
                os.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return this.socket;
    }
}
