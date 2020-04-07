package com.agilliza.consumer;

import com.agilliza.connection.JmsConnection;
import com.agilliza.exception.JmsConnectionException;
import com.agilliza.listener.JmsMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.Objects;

public class JmsMessageConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageConsumer.class);

    private Connection connection;
    private Session session;

    public JmsMessageConsumer() {
        this.connection = JmsConnection.getInstance().createConnection();
    }

    public JmsMessageConsumer(String clientID) {
        this.connection = JmsConnection.getInstance().createConnection(clientID);
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

    public MessageConsumer createMessageConsumer(String name) {
        try {
            if (Objects.isNull(session)) {
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            }

            Destination destination = JmsConnection.getInstance().createDestination(name);
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new JmsMessageListener());

            return consumer;
        } catch (JMSException e) {
            String message = "Erro ao criar consumidor";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

    public MessageConsumer createMessageConsumer(String topicName, String durableSubscriber) {
         try {
            if (Objects.isNull(session)) {
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            }

            Topic topic = JmsConnection.getInstance().createTopic(topicName);
            MessageConsumer consumer = session.createDurableSubscriber(topic, durableSubscriber);
            consumer.setMessageListener(new JmsMessageListener());

            return consumer;
        } catch (JMSException e) {
            String message = "Erro ao criar consumidor";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

}
