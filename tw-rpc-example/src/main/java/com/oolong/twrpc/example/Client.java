package com.oolong.twrpc.example;

import com.oolong.twrpc.client.RpcClient;

/**
 * @author oolong
 */
public class Client {
    public static void main(String[] args) {
        RpcClient client = new RpcClient();
        CalcServer service = client.getProxy(CalcServer.class);

        int r1 = service.add(1, 10);
        int r2 = service.sub(8, 6);

        System.out.println(r1);
        System.out.println(r2);

    }
}
