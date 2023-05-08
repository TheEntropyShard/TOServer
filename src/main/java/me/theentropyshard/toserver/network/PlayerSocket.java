package me.theentropyshard.toserver.network;

import me.theentropyshard.toserver.network.protocol.CommandEncoder;
import me.theentropyshard.toserver.network.protocol.CommandHandler;
import me.theentropyshard.toserver.network.protocol.codec.ControlRootCodec;
import me.theentropyshard.toserver.network.protocol.protection.PrimitiveProtectionContext;
import me.theentropyshard.toserver.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Queue;

public class PlayerSocket {
    private final Socket socket;

    public static CommandEncoder encoder;
    public static Socket sckt;

    private CommandHandler commandHandler;
    private CommandEncoder commandEncoder;
    private boolean sendPolicyFile;

    public PlayerSocket(Socket socket) {
        sckt = this.socket = socket;
        this.commandHandler = new ControlCommandHandler();
        encoder = this.commandEncoder = new CommandEncoder(new ControlRootCodec(), this.commandHandler, PrimitiveProtectionContext.INSTANCE);
    }

    public void processNetwork() {
        this.receiveData();
        this.sendData();
    }

    private void receiveData() {
        if(this.socket.isClosed()) {
            return;
        }

        try {
            byte[] buffer = new byte[4096];
            ByteBuffer data = ByteBuffer.wrap(buffer);

            InputStream is = this.socket.getInputStream();
            int nRead = is.read(buffer);

            if(nRead == -1) {
                System.out.println("Client disconnected");
                this.socket.close();
                return;
            }

            if(new String(buffer, StandardCharsets.UTF_8).contains("policy-file-request")) {
                this.sendPolicyFile = true;
                return;
            }

            this.commandEncoder.decodeAndExecuteCommandsFromServer(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendData() {
        if(this.socket.isClosed()) {
            return;
        }

        try {

            OutputStream os = this.socket.getOutputStream();

            // For some reason this does not work
            if(this.sendPolicyFile) {
                os.write(Utils.POLICY_FILE);
                os.flush();
                this.sendPolicyFile = false;
            }

            Queue commandsToServer = this.commandEncoder.getCommandsToServer();

            for(Object command : commandsToServer) {
                ByteBuffer data = (ByteBuffer) command;
                byte[] cmd = data.array();
                cmd = Arrays.copyOfRange(cmd, 0, cmd[1] + 2);
                //cmd[0] -= 2;
                System.out.println("Sending command: " + Arrays.toString(cmd));
                os.write(cmd);
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
