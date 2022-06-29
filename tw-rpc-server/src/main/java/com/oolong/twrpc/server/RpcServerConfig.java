package com.oolong.twrpc.server;

import com.oolong.twrpc.codec.Decoder;
import com.oolong.twrpc.codec.Encoder;
import com.oolong.twrpc.codec.JSONDecoder;
import com.oolong.twrpc.codec.JSONEncoder;
import com.oolong.twrpc.transport.HTTPTransportServer;
import com.oolong.twrpc.transport.TransportServer;
import lombok.Data;

/**
 * server配置
 * 代表使用哪个网络模块
 * 代表使用哪个序列化实现
 * 代表使用哪个监听端口
 * @author oolong
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;
    private Class<? extends Encoder> encodeClass = JSONEncoder.class;
    private Class<? extends Decoder> decodeClass = JSONDecoder.class;
    private int port = 3000;

}
