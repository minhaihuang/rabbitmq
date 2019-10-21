package com.hhm.rabbitmq.t05topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * create by huanghaimin on 2019-10-21
 * 主题交换器。
 * 可根据自己感心情的部分内容进行订阅。订阅规则，以.号区分。例：aaa.bbb.ccc
 *  可用*或#。*代表一个单词。#代表一个或多个单词。
 *  例：*.bbb.* 。那么a.bbb.cc是匹配的。bbb是不匹配的。
 */
public class ReceiveLogsTopic2 {
    private static final String LOG_LEVEL_EXCHANGE_NAME = "log_topic";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(LOG_LEVEL_EXCHANGE_NAME,"topic");

        // 对info和warning感兴趣的队列
        // 队列名称
        String queueName = channel.queueDeclare().getQueue();
        // 队列和交换器绑定。
        /**
         * 对所有handsome开头的日志感兴趣
         */
        String[] logLevels = new String[]{"handsome.#"};
        String routingKey;
        for (String logLevel : logLevels) {
            routingKey = logLevel;
            channel.queueBind(queueName,LOG_LEVEL_EXCHANGE_NAME,routingKey);
        }

        System.out.println("waiting for message");

        DeliverCallback deliverCallback = (consumerTag,delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        // 接收消息
        boolean autoAck = true; // 消息处理机制
        channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> { });
    }
}
