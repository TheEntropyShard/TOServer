package me.theentropyshard.toserver.network.protocol.codec.info;

import me.theentropyshard.toserver.network.protocol.ICodecInfo;

public class SetCodecInfo extends CodecInfo {
    public ICodecInfo elementCodecInfo;

    public SetCodecInfo(ICodecInfo var1, boolean var2) {
        super(var2);
        this.elementCodecInfo = var1;
    }

    @Override
    public CodecInfo copy(boolean var1) {
        return new SetCodecInfo(this.elementCodecInfo, var1);
    }

    public final ICodecInfo getElementCodecInfo() {
        return this.elementCodecInfo;
    }

    public final void setElementCodecInfo(ICodecInfo var1) {
        this.elementCodecInfo = var1;
    }
}
