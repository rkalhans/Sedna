package com.rohitkalhans.sedna.test;

import com.rohitkalhans.sedna.controllers.EventHandler;
import com.rohitkalhans.sedna.io.OutputCollector;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohit.kalhans on 23/12/14.
 */
public class TestEventHandler implements EventHandler {
    @Override
    public void executeEvent(Message event, OutputCollector collector) {
        String message;
        if (event instanceof TextMessage)
            try {
                message = ((TextMessage) event).getText();
                message = message + "-handled by-" + Thread.currentThread().getName();
                String msg = new String(message.getBytes());
                String s[]= msg.split(",");
                collector.write(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
    }
}