package com.sivsivsree.reatimeandroid;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    private final static String QUEUE_NAME = "new_task";

    public void send(String i) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.47.153");
        factory.setUsername("root");
        factory.setPassword("root");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "Hello World!" + i;
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        //Toast.makeText(context, "Data Send.", Toast.LENGTH_SHORT);

        channel.close();
        connection.close();
    }
}

