package com.hhm.rabbitmq.t02workqueue;

import com.rabbitmq.client.*;

/**
 * create by huanghaimin on 2019-10-21
 */
public class Worker {
    private static final String QUEUE_NAME = "task_queue";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 一次只处理一个消息
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(),"UTF-8");
            System.out.println("message=>"+message);
            doWork(message);
        };

        // 消息确认机制
        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME,autoAck,deliverCallback,consumerTag->{});
    }

    private static void doWork(String task){
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
