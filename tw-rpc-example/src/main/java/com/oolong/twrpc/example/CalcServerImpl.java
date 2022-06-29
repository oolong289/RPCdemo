package com.oolong.twrpc.example;

/**
 * @author oolong
 */
public class CalcServerImpl implements CalcServer {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }
}
