package com.oolong.twrpc.server;

import com.oolong.twrpc.Request;
import com.oolong.twrpc.common.utils.ReflectionUtils;

/**
 * 调用具体的服务
 * @author oolong
 */
public class ServiceInvoker {
    public Object invoke(ServiceInstance serviceInstance, Request request) {
        return ReflectionUtils.invoke(serviceInstance.getTarget(), serviceInstance.getMethod(), request.getParameters());
    }
}
