package com.oolong.twrpc.server;


import com.oolong.twrpc.Request;
import com.oolong.twrpc.ServiceDescriptor;
import com.oolong.twrpc.common.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 管理rpc暴露的服务
 *
 * @author oolong
 */
@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    /**
     * 将interfaceClass中的所有服务都注册到service中
     *
     * @param interfaceClass 接口的类型
     * @param bean           实现接口的对象（单例）
     */
    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : methods) {
            ServiceInstance sis = new ServiceInstance(bean, method);
            ServiceDescriptor sdp = ServiceDescriptor.from(interfaceClass, method);
            services.put(sdp, sis);
            log.info("register service: {} {}", sdp.getClazz(), sdp.getMethod());
        }
    }


    /**
     * @param request RPC的请求
     * @return sdp对应的实例
     */
    public ServiceInstance lookup(Request request) {
        ServiceDescriptor sdp = request.getService();
        return services.get(sdp);
    }

}



