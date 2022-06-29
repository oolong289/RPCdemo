package com.oolong.twrpc;

import lombok.Data;

/**
 * 表示RPC的一个请求；
 *
 * @author oolong
 */
@Data
public class Request {
    private ServiceDescriptor service;
    private Object[] parameters;
}
