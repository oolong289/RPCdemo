package com.oolong.twrpc;

import lombok.Data;

/**
 * 表示PRC的返回
 *
 * @author oolong
 */
@Data
public class Response {
    /**
     * 服务返回编码，0成功，非0失败
     */
    private int code = 0;
    /**
     * 服务返回错误信息
     */
    private String message = "ok";
    /**
     * 服务返回数据
     */
    private Object data;

}
