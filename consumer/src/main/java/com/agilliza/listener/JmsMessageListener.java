package com.agilliza.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class JmsMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageListener.class);

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            LOGGER.info("Mensagem: {}", textMessage.getText());
        } catch (JMSException e) {
            LOGGER.error("Erro ao fazer ao fazer CAST TextMessage", e);
        }
    }
}
