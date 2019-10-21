package com.hhm.rabbitmq.t01helloword;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息发生方
 */
public class Send {
    // 管道名称
    public static final String QUEEN_NAME = "hello";
    public static void main(String[] args) throws Exception{
        // 可以创建到服务器的连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        // 因为Connection和Channel都实现了java.io.Closeable。这样我们就不需要在代码中明确地关闭它们。
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            //发布消息。声明队列是幂等的 - 只有在它不存在的情况下才会创建它。消息内容是一个字节数组，因此您可以编码您喜欢的任何内容。
            channel.queueDeclare(QUEEN_NAME,false,false,false,null);
            String message = "hello world";
            channel.basicPublish("",QUEEN_NAME,null,message.getBytes());

            System.out.println("发送成功");
        }
    }
}
