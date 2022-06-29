package com.oolong.twrpc.codec;

import com.alibaba.fastjson.JSON;

/**
 * 基于JSON的反序列化实现
 *
 * @author oolong
 */
public class JSONEncoder implements Encoder {
    /**
     * 通过fastJSON将对象转换成JSON byte数组。
     *
     * @param obj
     * @return
     */
    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);
    }
}
