package com.agilliza.connection;

import com.agilliza.exception.JmsConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Objects;

public class JmsConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConnection.class);

    private static JmsConnection instance;

    private InitialContext initialContext;
    private ConnectionFactory connectionFactory;

    public JmsConnection() {
        try {
            initialContext = new InitialContext();
            connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        } catch (NamingException e) {
            String message = "Erro ao carregar a fabrica de conexão";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

    public static synchronized JmsConnection getInstance() {
        if (Objects.isNull(instance)) {
            instance = new JmsConnection();
        }

        return instance;
    }

    public Connection createConnection() {
        return createConnection(null);
    }

    public Connection createConnection(String clientID) {
        try {
            Connection connection = connectionFactory.createConnection();

            if (Objects.nonNull(clientID) && !clientID.isEmpty()) {
                connection.setClientID(clientID);
            }

            connection.start();

            return connection;
        } catch (JMSException e) {
            String message = "Erro ao criar ao conexão";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

    public Destination createDestination(String name) {
        try {
            return (Destination) initialContext.lookup(name);
        } catch (NamingException e) {
            String message = "Erro ao carregar Destination";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

    public Topic createTopic(String name) {
        try {
            return (Topic) initialContext.lookup(name);
        } catch (NamingException e) {
            String message = "Erro ao carregar Topic";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

    public void close() {
        try {
            closeInitialContext();
        } catch (NamingException e) {
            String message = "Erro ao fechar conexão";
            LOGGER.error(message, e);
            throw new JmsConnectionException(message, e);
        }
    }

    private void closeInitialContext() throws NamingException {
        if (Objects.nonNull(initialContext)) {
            initialContext.close();
        }
    }
}
