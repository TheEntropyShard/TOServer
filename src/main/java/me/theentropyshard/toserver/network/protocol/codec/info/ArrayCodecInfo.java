package me.theentropyshard.toserver.network.protocol.codec.info;

import me.theentropyshard.toserver.network.protocol.ICodecInfo;

public class ArrayCodecInfo extends CollectionCodecInfo {
    public ArrayCodecInfo(ICodecInfo elementCodecInfo, boolean optional) {
        super(elementCodecInfo, optional);
    }

    public ArrayCodecInfo copy(boolean optional) {
        return new ArrayCodecInfo(this.getElementCodecInfo(), optional);
    }
}
