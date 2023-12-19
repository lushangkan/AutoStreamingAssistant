package cn.cutemc.autostreamingassistant.utils;

import net.minecraft.network.PacketByteBuf;

import java.nio.ByteBuffer;

public class BufferUtils {

    /**
     * 将ByteBuffer转换为byte数组，使用fori解决Buffer实际大小大于capacity大小的问题
     * @param buffer ByteBuffer
     * @return byte数组
     */
    public static byte[] toBytes(ByteBuffer buffer) {
        byte[] bytes = new byte[buffer.capacity()];
        for (int i = 0; i < buffer.capacity(); i++) {
            bytes[i] = buffer.get(i);
        }
        return bytes;
    }

    /**
     * 将PacketByteBuf转换为byte数组，使用fori解决Buffer实际大小大于capacity大小的问题
     * @param buffer PacketByteBuf
     * @return byte数组
     */
    public static byte[] toBytes(PacketByteBuf buffer) {
        return toBytes(buffer.nioBuffer());
    }
}
