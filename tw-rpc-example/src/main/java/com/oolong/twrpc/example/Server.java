package com.oolong.twrpc.example;

import com.oolong.twrpc.server.RPCServer;

/**
 * @author oolong
 */
public class Server {
    public static void main(String[] args) {
        RPCServer server = new RPCServer();
        server.register(CalcServer.class, new CalcServerImpl());
        server.start();


    }
}
