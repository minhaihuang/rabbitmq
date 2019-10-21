package com.hhm.rabbitmq.t03public_subscribe;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * create by huanghaimin on 2019-10-21。
 * 发布订阅模式：产生日志
 */
public class EmitLog {
    private static final String EXCAHNGE_NAME = "logs";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                ){
            // 声明交换器
            channel.exchangeDeclare(EXCAHNGE_NAME, "fanout");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("please enter String:");
                String message = scanner.next();
                // 发布日志 。 publishing to a non-existing exchange is forbidden.
                channel.basicPublish(EXCAHNGE_NAME,"",null,message.getBytes("UTF-8"));
            }

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
