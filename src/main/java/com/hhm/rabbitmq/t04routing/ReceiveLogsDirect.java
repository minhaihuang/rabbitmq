package com.hhm.rabbitmq.t04routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

/**
 * create by huanghaimin on 2019-10-21
 * 分别处理不同级别的日志。对info、warning和error级别的日志感兴趣
 * exchanger声明为direct。队列和交换器绑定时注明routingKey
 */
public class ReceiveLogsDirect {
    private static final String LOG_LEVEL_EXCHANGE_NAME = "log_direct";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(LOG_LEVEL_EXCHANGE_NAME,"direct");

        // 对info和warning感兴趣的队列
        // 队列名称
        String queueName = channel.queueDeclare().getQueue();
        // 队列和交换器绑定
        String[] logLevels = new String[]{"info","warning","error"};
        for (String logLevel : logLevels) {
            channel.queueBind(queueName,LOG_LEVEL_EXCHANGE_NAME,logLevel);
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
