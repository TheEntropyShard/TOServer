package me.theentropyshard.toserver.network.protocol.codec.info;

import me.theentropyshard.toserver.network.protocol.ICodecInfo;

public class MapCodecInfo extends CodecInfo {
    private ICodecInfo keyCodecInfo;
    private ICodecInfo valueCodecInfo;

    public MapCodecInfo(ICodecInfo var1, ICodecInfo var2, boolean var3) {
        super(var3);
        this.keyCodecInfo = var1;
        this.valueCodecInfo = var2;
    }

    @Override
    public CodecInfo copy(boolean var1) {
        return new MapCodecInfo(this.keyCodecInfo, this.valueCodecInfo, var1);
    }

    public final ICodecInfo getKeyCodecInfo() {
        return this.keyCodecInfo;
    }

    public final ICodecInfo getValueCodecInfo() {
        return this.valueCodecInfo;
    }

    public final void setKeyCodecInfo(ICodecInfo var1) {
        this.keyCodecInfo = var1;
    }

    public final void setValueCodecInfo(ICodecInfo var1) {
        this.valueCodecInfo = var1;
    }
}
