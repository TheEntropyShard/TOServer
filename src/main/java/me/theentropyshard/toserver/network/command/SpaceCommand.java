package me.theentropyshard.toserver.network.command;

import me.theentropyshard.toserver.network.protocol.ProtocolBuffer;

public class SpaceCommand {
    public static final byte PRODUCE_HASH = 3;
    private final long methodId;
    private final long objectId;
    private final ProtocolBuffer protocolBuffer;

    public SpaceCommand(long var1, long var3, ProtocolBuffer var5) {
        this.objectId = var1;
        this.methodId = var3;
        this.protocolBuffer = var5;
    }

    public final long getMethodId() {
        return this.methodId;
    }

    public final long getObjectId() {
        return this.objectId;
    }

    public final ProtocolBuffer getProtocolBuffer() {
        return this.protocolBuffer;
    }
}
