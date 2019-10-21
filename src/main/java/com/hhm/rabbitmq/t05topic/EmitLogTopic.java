package com.hhm.rabbitmq.t05topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * create by huanghaimin on 2019-10-21
 * 主题交换器。
 * 可根据自己感心情的部分内容进行订阅。订阅规则，以.号区分。例：aaa.bbb.ccc
 *  可用*或#。*代表一个单词。#代表一个或多个单词。
 *  例：*.bbb.* 。那么a.bbb.cc是匹配的。bbb是不匹配的。
 */
public class EmitLogTopic {
    private static final String LOG_LEVEL_EXCHANGE_NAME = "log_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(LOG_LEVEL_EXCHANGE_NAME,"topic");

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
