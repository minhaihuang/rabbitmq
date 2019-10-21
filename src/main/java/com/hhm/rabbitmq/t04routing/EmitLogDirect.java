package com.hhm.rabbitmq.t04routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * create by huanghaimin on 2019-10-21。
 * 直连交换器(direct)。处理不同的日志级别。
 */
public class EmitLogDirect {
    private static final String LOG_LEVEL_EXCHANGE_NAME = "log_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(LOG_LEVEL_EXCHANGE_NAME,"direct");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("please enter log level and message:");
            // 日志级别。队列名称
            String logLevelQueueName = scanner.next();
            // 日志内容
            String message = scanner.next();

            System.out.println(logLevelQueueName+"==>"+message);

            // 发布消息
            channel.basicPublish(LOG_LEVEL_EXCHANGE_NAME,logLevelQueueName,null,message.getBytes("UTF-8"));
        }
    }
}
