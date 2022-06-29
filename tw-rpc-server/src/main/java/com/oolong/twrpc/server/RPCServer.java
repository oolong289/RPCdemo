package com.oolong.twrpc.server;

import com.oolong.twrpc.Request;
import com.oolong.twrpc.Response;
import com.oolong.twrpc.codec.Encoder;
import com.oolong.twrpc.codec.Decoder;
import com.oolong.twrpc.common.utils.ReflectionUtils;
import com.oolong.twrpc.transport.RequestHandler;
import com.oolong.twrpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author oolong
 */
@Slf4j
public class RPCServer {

    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RPCServer() {
        this(new RpcServerConfig());
    }

    public RPCServer(RpcServerConfig config) {
        this.config = config;

        // net
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);

        // codec
        this.encoder = ReflectionUtils.newInstance(config.getEncodeClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecodeClass());

        // service
        this.serviceInvoker = new ServiceInvoker();
        this.serviceManager = new ServiceManager();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.net.start();
    }

    public void stop() {
        this.net.stop();
    }

    /**
     * 通过receive读取到Request请求序列化之后的数据，读取之后通过serviceInvoker调用，再通过toResp返回
     */
    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResp) {
            Response resp = new Response();
            try {
                byte[] InBytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(InBytes, Request.class);
                log.info("get request: {}", request);

                ServiceInstance sis = serviceManager.lookup(request);
                Object ret = serviceInvoker.invoke(sis, request);
                resp.setData(ret);

            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                resp.setCode(1);
                resp.setMessage("RpcServer got error: "
                        + e.getClass().getName()
                        + " : " + e.getMessage()
                );
            } finally {

                try {
                    byte[] outBytes = encoder.encode(resp);
                    toResp.write(outBytes);

                    log.info("response client");
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };
}
