package com.oolong.twrpc.client;

import com.oolong.twrpc.codec.Decoder;
import com.oolong.twrpc.codec.Encoder;
import com.oolong.twrpc.common.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

/**
 * @author oolong
 */
public class RpcClient {
    private RpcClientConfig config;
    private TransportSelector selector;
    private Decoder decoder;
    private Encoder encoder;

    public RpcClient() {
        this(new RpcClientConfig());
    }

    public RpcClient(RpcClientConfig config) {
        this.config = config;

        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderCLass());
        this.selector = ReflectionUtils.newInstance(config.getSelectorClass());

        this.selector.inti(
                this.config.getServers(),
                this.config.getConnectCount(),
                this.config.getTransportClass()
        );
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoker(clazz, selector, decoder, encoder)
        );
    }
}
