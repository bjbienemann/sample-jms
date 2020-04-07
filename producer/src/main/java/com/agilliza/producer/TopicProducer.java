package com.agilliza.producer;

import com.agilliza.connection.JmsConnection;

import java.text.MessageFormat;
import java.util.Random;

public class TopicProducer {

    public static void main(String[] args) {
        JmsMessageProducer jmsMessageProducer = new JmsMessageProducer("order");

        Random random = new Random();
        int number = random.nextInt(100);
        String textMessage = MessageFormat.format("\"id\":\"{0}\"", number);
        jmsMessageProducer.sendTextMessage(textMessage);

        jmsMessageProducer.close();
        JmsConnection.getInstance().close();
    }
}
