package com.hhm.rabbitmq.t02workqueue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class NewTask {
    public static final String QUEEN_NAME = "task_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 准备连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                ){
                boolean durable = true; // 持久化。make sure the queue never lost.
                boolean exclusive = false;
                boolean autoDelete = false;
                channel.queueDeclare(QUEEN_NAME,durable,exclusive,autoDelete,null);
                System.out.println("sender start");
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("please enter String:");
                    String message = scanner.next();
                    // MessageProperties.PERSISTENT_TEXT_PLAIN：将任务持久化
                    channel.basicPublish("",QUEEN_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes("UTF-8"));
                }
        }

    }
}
