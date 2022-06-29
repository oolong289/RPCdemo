package com.oolong.twrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 表示网络传输的一个节点
 *
 * @author oolong
 */
@Data
@AllArgsConstructor
public class Peer {
    private String host;
    private int port;
}
