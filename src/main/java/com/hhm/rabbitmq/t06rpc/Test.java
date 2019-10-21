package com.hhm.rabbitmq.t06rpc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * create by huanghaimin on 2019-10-21
 */
public class Test {
    public static void main(String[] args) throws Exception {
        RPCClient fibonacciRpc = new RPCClient();

        System.out.println(" [x] Requesting fib(30)");
        String response = fibonacciRpc.call("30");
        System.out.println(" [.] Got '" + response + "'");

        fibonacciRpc.close();
    }
}
