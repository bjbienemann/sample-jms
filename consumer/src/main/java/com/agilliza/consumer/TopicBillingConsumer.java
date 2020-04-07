package com.agilliza.consumer;

import com.agilliza.connection.JmsConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class TopicBillingConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicBillingConsumer.class);

    public static void main(String[] args) {
        JmsMessageConsumer jmsMessageConsumer = new JmsMessageConsumer("billing");
        jmsMessageConsumer.createMessageConsumer("order", "assinatura");

        LOGGER.warn("Precione enter para encerrar o programa");
        new Scanner(System.in).nextLine();

        jmsMessageConsumer.close();
        JmsConnection.getInstance().close();
    }

}
