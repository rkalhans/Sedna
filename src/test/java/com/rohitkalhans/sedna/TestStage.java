package com.rohitkalhans.sedna;

import com.rohitkalhans.sedna.config.QueueConfig;
import com.rohitkalhans.sedna.config.StageConfig;
import com.rohitkalhans.sedna.config.ThreadPoolConfig;
import com.rohitkalhans.sedna.io.SedaQueue;
import com.rohitkalhans.sedna.stage.Stage;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by rohit.kalhans on 23/12/14.
 */
public class TestStage {

    private static ExecutorService threadpool = new ThreadPoolExecutor(20,
            30,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(100));

    public static void main(String[] args) throws InterruptedException {
        Stage stage = new Stage(new StageConfig(new ThreadPoolConfig(), new QueueConfig()), new TestEventHandler());
        try {
            stage.start();
            for (int i = 0; i < 100; i++) {
                threadpool.submit(new TestProducer());
            }
            threadpool.shutdown();
            new TestConsumer().run();
            stage.stop();

        } catch (Exception ex) {
            System.out.println("Kuch fat gaya " + ex.getMessage());
        }
    }

    public static class TestProducer implements Runnable {
        public void run() {
            try {
                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue(SedaQueue.INBOUND_QUEUE);

                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                // Create a messages
                String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                TextMessage message = session.createTextMessage(text);

                // Tell the producer to send the message
                System.out.println("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
                producer.send(message);

                // Clean up
                session.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }

    public static class TestConsumer implements ExceptionListener {
        public void run() {
            try {

                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();
                connection.setExceptionListener(this);


                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue(SedaQueue.OUTBOUND_QUEUE);

                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);

                int i = 100;
                // Wait for a message
                while (i-- > 0) {
                    Message message = consumer.receive(1000);
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String text = textMessage.getText();
                        System.out.println("Received: " + text);
                    } else {
                        System.out.println("Received: " + message);
                    }
                }
                consumer.close();
                session.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }

        @Override
        public void onException(JMSException e) {
            System.err.println("Excception" + e);
            e.printStackTrace();

        }
    }

}