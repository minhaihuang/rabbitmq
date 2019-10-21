package com.hhm.rabbitmq.t03public_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * create by huanghaimin on 2019-10-21。
 * 日志的接收者
 */
public class ReceiveLogs {
    private static final String EXCAHNGE_NAME = "logs";

    // if close the connection,this program will be finished.

    public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明交换器
        channel.exchangeDeclare(EXCAHNGE_NAME, "fanout");
        // 随机产生一个队列，并返回名称。
        //when we supply no parameters to queueDeclare() we create a non-durable, exclusive, autodelete queue with a generated name:
        String queueName = channel.queueDeclare().getQueue();
        // 将队列绑定到交换器上。
        channel.queueBind(queueName,EXCAHNGE_NAME,"");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag,delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
