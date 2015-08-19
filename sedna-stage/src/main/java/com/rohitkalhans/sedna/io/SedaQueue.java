package com.rohitkalhans.sedna.io;

import com.rohitkalhans.sedna.config.QueueConfig;
import com.rohitkalhans.sedna.stage.Lifecycle;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;

/**
 * Created by rohit.kalhans on 21/12/14.
 */

/**
 * Abstraction of ActiveMQ which will be used as input and output queues.
 * The inbound and the outbound queues will be used by default unless the
 * output is directed to a different queues as specified in the OutputCollector.write().
 */

public class SedaQueue implements Lifecycle {

    private String consumerConnectionString;
    private String producerConnectionString;

    /* threadlocal connection to the the ActiveMQ */
    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();
    Connection connection;
    Destination destination;
    MessageConsumer consumer;
    Session session;
    /* instance of ActiveMQ Broker Service */
    private BrokerService brokerService;
    /* Config that will be required to instantiate the queue. */
    private QueueConfig config;

    private QueueConfig outConfig;
    /* Active MQ connection factory. */
    private ActiveMQConnectionFactory connectionFactory;

    /**
     * Constructor to initaialize a queue
     *
     * @param config Queue Config object.
     */

    public SedaQueue(QueueConfig config, QueueConfig outConfig, boolean needQueue) {
        this.config = config;
        this.outConfig = outConfig;

        // "By default we will use Active MQ All those who wish
        // to oppose this must speak now or must hold their
        // silence for ever.
        // No one spoke. Good! By the power vested in me (as the programmer
        // of this software), I now pronounce ActiveMQ DEFAULT QUEUE for SEDNA
        // you may now initialize the broker"
        if(needQueue)
            brokerService = new BrokerService();

        // ------- Somewhere in the deep dark corner of his house the sobs of ZeroMQ was heard by a few ---------

        consumerConnectionString = "nio://"+config.getHost()+":"+config.getQueuePort();
        producerConnectionString = "nio://"+outConfig.getHost()+":"+outConfig.getQueuePort();
        connectionFactory = new ActiveMQConnectionFactory(consumerConnectionString);
    }

    /**
     * @return connection that will be used by this thread.
     * @throws JMSException When it is unable to create a connection
     *                      to ActiveMQ
     */

    private Connection getConnection() throws JMSException {
        if (threadConnection.get() == null)
            threadConnection.set(connectionFactory.createConnection());
        return threadConnection.get();
    }

    /**
     * Starts the queue. Initialize the brokerservice with a TCP connector.
     * Creates a session and creates a new consumer.
     */

    @Override
    public void start() {
        try {
            if (brokerService != null)
            {
                brokerService.addConnector(this.consumerConnectionString);
                brokerService.start();
            }
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue(config.getQueueName());
            consumer = session.createConsumer(destination);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException(" The current Queue implementation cant be paused. ");

    }

    /**
     * Stop the broker.
     */
    @Override
    public void stop() {
        try {
            brokerService.stop();
        } catch (Exception ex) {

        }
    }

    /**
     * Write to the output queue. Writes to default queue.
     *
     * @param message
     * @throws JMSException when it is unable to write to the output queue.
     */
    public void writeOut(String message) throws JMSException {
        writeOut(message, null);
    }

    /**
     * Writes to the output queue specified by the writer.
     *
     * @param message
     * @param OutboundQueue
     * @throws JMSException when it is unable to write to the output queue.
     */

    public void writeOut(String message, String OutboundQueue) throws JMSException {
        Connection con = getConnection();
        con.start();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(outConfig.getQueueName());
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.send(destination, session.createTextMessage(message));
        session.close();
        //con.stop();
    }

    /**
     * Register a message listener to this queue.  The message listener's onMessage()
     * will be called every time an event is seen.
     *
     * @param listener
     * @return
     */

    public boolean registerListener(MessageListener listener) {
        try {
            consumer.setMessageListener(listener);
        } catch (JMSException ex) {
            return false;
        }
        return true;
    }

}
