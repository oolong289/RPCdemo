package com.oolong.twrpc.codec;

/**
 * 反序列化
 *
 * @author oolong
 */
public interface Decoder {
    <T> T decode(byte[] bytes, Class<T> clazz);
}
