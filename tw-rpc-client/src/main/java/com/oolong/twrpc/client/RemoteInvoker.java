package com.oolong.twrpc.client;

import com.oolong.twrpc.Request;
import com.oolong.twrpc.Response;
import com.oolong.twrpc.ServiceDescriptor;
import com.oolong.twrpc.codec.Decoder;
import com.oolong.twrpc.codec.Encoder;
import com.oolong.twrpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 *
 * @author oolong
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Class clazz;
    private TransportSelector selector;
    private Decoder decoder;
    private Encoder encoder;

    public RemoteInvoker(Class clazz, TransportSelector selector, Decoder decoder, Encoder encoder) {
        this.clazz = clazz;
        this.selector = selector;
        this.decoder = decoder;
        this.encoder = encoder;
    }

    @Override
    public Object invoke(Object proxy,
                         Method method,
                         Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response resp = invokeRemote(request);
        if (resp == null || resp.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote: " + resp);
        }


        return resp.getData();
    }

    /**
     * 网络传输
     *
     * @param request
     * @return
     */
    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response resp = null;
        try {
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream receive = client.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(receive, receive.available());
            resp = decoder.decode(inBytes, Response.class);

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            resp = new Response();
            resp.setCode(1);
            resp.setMessage("RecClient got error: " + e.getClass() + " : " + e.getMessage());
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }
        return resp;
    }
}
