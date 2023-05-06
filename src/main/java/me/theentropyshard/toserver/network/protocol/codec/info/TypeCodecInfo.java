package me.theentropyshard.toserver.network.protocol.codec.info;

import java.util.Objects;

public class TypeCodecInfo extends CodecInfo {
    public final Class type;

    public TypeCodecInfo(Class type, boolean optional) {
        super(optional);
        this.type = type;
    }

    @Override
    public CodecInfo copy(boolean var1) {
        return new TypeCodecInfo(this.type, var1);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        TypeCodecInfo that = (TypeCodecInfo) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public final Class getType() {
        return this.type;
    }
}
