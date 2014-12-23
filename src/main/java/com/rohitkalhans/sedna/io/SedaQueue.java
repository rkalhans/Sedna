package com.rohitkalhans.sedna.io;

import com.rohitkalhans.sedna.config.QueueConfig;
import com.rohitkalhans.sedna.stage.Lifecycle;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;

/**
 * Created by rohit.kalhans on 21/12/14.
 */

public class SedaQueue implements Lifecycle {
    public static final String INBOUND_QUEUE= "SEDNA_IN";
    public static final String OUTBOUND_QUEUE= "SEDNA_OUT";
    private BrokerService brokerService;
    private QueueConfig config;
    private ActiveMQConnectionFactory connectionFactory;
    private static ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();

    private Connection getConnection() throws JMSException{
       if(threadConnection.get() == null)
           threadConnection.set(connectionFactory.createConnection());
        return threadConnection.get();
    }


    public SedaQueue(QueueConfig config){
        this.config= config;

        // "By default we will use Active MQ All those who wish
        // to oppose this must speak now or must hold their
        // silence for ever.
        // No one spoke. Good! By the power vested in me (as the programmer
        // of this software), I now pronounce ActiveMQ DEFAULT QUEUE for SEDNA
        // you may now initialize the broker"

       brokerService = new BrokerService();

        // ------- Somewhere in the deep dark corner of his house the sobs of ZeroMQ was heard by a few ---------

        connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
    }


    @Override
    public boolean start(){
        try {
            brokerService.addConnector("tcp://localhost:" + config.getQueuePort());
            brokerService.start();
            return true;
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void pause() {
        throw new UnsupportedOperationException(" The current Queue implementation cant be paused. ");

    }

    @Override
    public boolean stop() {
        try {
            brokerService.stop();
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }

    public void writeOut(String message) throws JMSException
    {
        writeOut(message,null);
    }


    public void  writeOut(String message, String OutboundQueue) throws JMSException {
        Connection con = getConnection();
        con.start();
        Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue((OutboundQueue== null)?OUTBOUND_QUEUE:OutboundQueue);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producer.send(destination,session.createTextMessage(message));
        session.close();
        //con.stop();
    }


    // todo: fix this  method since this not the correct way of adding a message listener.
    public boolean registerListener( MessageListener listener)
    {
        try {
            Connection con = getConnection();
            con.start();
            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(OUTBOUND_QUEUE);
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(listener);
        }catch (JMSException ex){
            return false;
        }
        return true;
    }

    //todo: figure out how to unregister a dispacher.

}
