package com.oolong.twrpc.transport;

/**
 * 启动，监听端口
 * 接收请求
 * 关闭监听
 *
 * @author oolong
 */
public interface TransportServer {
    void init(int port, RequestHandler handler);

    public void start();


    public void stop();
}
