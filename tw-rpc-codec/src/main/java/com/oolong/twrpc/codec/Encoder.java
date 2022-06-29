package com.oolong.twrpc.codec;

/**
 * 序列化
 * @author oolong
 */
public interface Encoder {
    byte[] encode(Object obj);
}
