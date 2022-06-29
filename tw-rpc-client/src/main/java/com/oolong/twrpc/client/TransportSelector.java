package com.oolong.twrpc.client;

import com.oolong.twrpc.Peer;
import com.oolong.twrpc.transport.TransportClient;

import java.util.List;

/**
 * 表示选择哪个Server去连接
 *
 * @author oolong
 */
public interface TransportSelector {

    /**
     * 初始化selector
     *
     * @param peers 可以连接的server断点信息
     * @param count client与server建立多少连接
     * @param clazz client实现class
     */
    void inti(List<Peer> peers, int count, Class<? extends TransportClient> clazz);

    /**
     * 选择一个transport与server做交互
     *
     * @return 网络client
     */
    TransportClient select();

    /**
     * 释放用完的client
     *
     * @param client transportClient
     */
    void release(TransportClient client);

    void close();
}
