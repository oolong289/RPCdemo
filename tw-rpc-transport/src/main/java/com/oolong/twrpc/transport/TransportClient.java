package com.oolong.twrpc.transport;

import com.oolong.twrpc.Peer;

import java.io.InputStream;

/**
 * 创建连接
 * 发送数据，等待响应
 * 关闭连接
 *
 * @author oolong
 */
public interface TransportClient {
    public void connect(Peer peer);

    InputStream write(InputStream data);

    public void close();
}
