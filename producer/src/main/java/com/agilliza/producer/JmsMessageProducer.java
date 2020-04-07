package com.agilliza.producer;

import com.agilliza.connection.JmsConnection;
import com.agilliza.exception.JmsConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.Objects;

public class JmsMessageProducer implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageProducer.class);

    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public JmsMessageProducer(String name) {
        connection = JmsConnection.getInstance().createConnection();
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = JmsConnection.getInstance().createDestination(name);
            messageProducer = session.createProducer(destination);
        } catch (JMSException e) {
            String message = "Erro ao criar consumidor";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

    public void close() {
        try {
            if (Objects.nonNull(session)) {
                session.close();

            }
            if (Objects.nonNull(connection)) {
                connection.close();
            }
        } catch (JMSException e) {
            String message = "Erro ao fechar sessão e/ou conexão";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

    public void sendTextMessage(String textMessage) {
        try {
            Message message = session.createTextMessage(textMessage);
            messageProducer.send(message);
        } catch (JMSException e) {
            String message = "Erro ao enviar mensagem";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }
}
