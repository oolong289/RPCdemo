package com.oolong.twrpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求的的handler
 *
 * @author oolong
 */
public interface RequestHandler {
    void onRequest(InputStream receive, OutputStream toResp);
}
