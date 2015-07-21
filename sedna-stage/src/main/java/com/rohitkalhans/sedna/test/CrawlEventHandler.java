package com.rohitkalhans.sedna.test;

import com.rohitkalhans.sedna.controllers.EventHandler;
import com.rohitkalhans.sedna.io.OutputCollector;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Created by rohit.kalhans on 23/12/14.
 */
public class CrawlEventHandler implements EventHandler {
    @Override
    public void executeEvent(Message event, OutputCollector collector) {
        String message;
        if (event instanceof TextMessage)
            try {
                message = ((TextMessage) event).getText();
                String msg = new String(message.getBytes());
                String s[]= msg.split(",");
                Thread.sleep(Integer.parseInt(s[0]));
                collector.write(message);
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}