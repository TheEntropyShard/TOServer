package me.theentropyshard.toserver.network.protocol.codec;

import me.theentropyshard.toserver.network.command.ControlCommand;
import me.theentropyshard.toserver.network.command.SpaceOpenedCommand;
import me.theentropyshard.toserver.network.command.SpaceOpenedCommandCodec;
import me.theentropyshard.toserver.network.command.control.client.HashRequestCommandCodec;
import me.theentropyshard.toserver.network.command.control.client.LogCommandCodec;
import me.theentropyshard.toserver.network.command.control.server.HashResponseCommandCodec;
import me.theentropyshard.toserver.network.command.control.server.OpenSpaceCommandCodec;
import me.theentropyshard.toserver.network.protocol.ICodec;
import me.theentropyshard.toserver.network.protocol.IProtocol;
import me.theentropyshard.toserver.network.protocol.ProtocolBuffer;
import me.theentropyshard.toserver.network.protocol.codec.info.TypeCodecInfo;

import java.util.HashMap;

public class ControlRootCodec implements ICodec {
    public ICodec byteCodec;
    public HashMap clientCommandCodecs = new HashMap();
    public HashMap serverCommandCodecs = new HashMap();

    public Object decode(ProtocolBuffer var1) {

        ICodec var3 = this.byteCodec;
        ICodec var2 = var3;
        if(var3 == null) {

            var2 = null;
        }

        Object var4 = var2.decode(var1);
        System.out.println("Command id " + var4);
        var2 = (ICodec) this.clientCommandCodecs.get(var4);
        if(var2 == null) {
            return null;
        } else {
            return var2.decode(var1);
        }
    }

    public void encode(ProtocolBuffer var1, Object var2) {

        if(var2 != null) {
            ControlCommand var5 = (ControlCommand) var2;
            ICodec var4 = this.byteCodec;
            ICodec var3 = var4;
            if(var4 == null) {

                var3 = null;
            }

            var3.encode(var1, var5.getId());
            if(this.serverCommandCodecs.containsKey(var5.getId())) {
                var3 = (ICodec) this.serverCommandCodecs.get(var5.getId());
                if(var3 != null) {
                    var3.encode(var1, var2);
                }

            } else {
                StringBuilder var6 = new StringBuilder();
                var6.append("Command with id ");
                var6.append(var5.getId());
                var6.append(" unknown");
                throw new RuntimeException(var6.toString());
            }
        } else {
            throw new NullPointerException("null cannot be cast to non-null type alternativa.client.network.command.ControlCommand");
        }
    }

    public void init(IProtocol var1) {
        this.byteCodec = var1.getCodec(Byte.TYPE, false);
        var1.registerCodec(new TypeCodecInfo(SpaceOpenedCommand.class, false), new SpaceOpenedCommandCodec());
        HashResponseCommandCodec var2 = new HashResponseCommandCodec();
        var2.init(var1);
        this.serverCommandCodecs.put(ControlCommand.SV_HASH_RESPONSE, var2);
        OpenSpaceCommandCodec var3 = new OpenSpaceCommandCodec();
        var3.init(var1);
        this.serverCommandCodecs.put(ControlCommand.SV_OPEN_SPACE, var3);
        HashRequestCommandCodec var4 = new HashRequestCommandCodec();
        var4.init(var1);
        this.clientCommandCodecs.put(ControlCommand.CL_HASH_REQUEST, var4);
        LogCommandCodec var5 = new LogCommandCodec();
        var5.init(var1);
        this.clientCommandCodecs.put(ControlCommand.CL_LOG, var5);
    }
}
