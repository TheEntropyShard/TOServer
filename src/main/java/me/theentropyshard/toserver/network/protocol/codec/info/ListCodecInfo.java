package me.theentropyshard.toserver.network.protocol.codec.info;

import me.theentropyshard.toserver.network.protocol.ICodecInfo;

public class ListCodecInfo extends CollectionCodecInfo {
    public ListCodecInfo(ICodecInfo codecInfo, boolean optional) {
        super(codecInfo, optional);
    }

    public ListCodecInfo copy(boolean optional) {
        return new ListCodecInfo(this.getElementCodecInfo(), optional);
    }
}
