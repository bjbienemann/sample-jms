package com.agilliza.producer;

import com.agilliza.connection.JmsConnection;

import java.text.MessageFormat;
import java.util.Random;

public class QueueProducer {

    public static void main(String[] args) {
        try (JmsMessageProducer jmsMessageProducer = new JmsMessageProducer("system")) {
            Random random = new Random();
            for (int i = 1; i <= 10; i++) {
                int version = random.nextInt(3);
                String textMessage = MessageFormat.format("{0},V{1}", "SYS", version);
                jmsMessageProducer.sendTextMessage(textMessage);
            }
        }

        JmsConnection.getInstance().close();
    }

}
