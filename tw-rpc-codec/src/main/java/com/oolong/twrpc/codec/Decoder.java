package com.oolong.twrpc.codec;

/**
 * εεΊεε
 *
 * @author oolong
 */
public interface Decoder {
    <T> T decode(byte[] bytes, Class<T> clazz);
}
