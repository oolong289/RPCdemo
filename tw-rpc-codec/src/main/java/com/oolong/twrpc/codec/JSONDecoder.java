package com.oolong.twrpc.codec;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author oolong
 */
public class JSONDecoder implements Decoder {
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
