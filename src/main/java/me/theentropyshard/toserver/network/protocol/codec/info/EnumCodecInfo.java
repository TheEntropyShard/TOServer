package me.theentropyshard.toserver.network.protocol.codec.info;

public class EnumCodecInfo extends TypeCodecInfo {
    public EnumCodecInfo(Class type, boolean optional) {
        super(type, optional);
    }

    public CodecInfo copy(boolean var1) {
        return new EnumCodecInfo(this.getType(), var1);
    }
}
