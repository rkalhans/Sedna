package com.rohitkalhans.sedna.io;

import javax.jms.JMSException;

/**
 * Created by rohit.kalhans on 23/12/14.
 */
public class OutputCollector {

    private SedaQueue queue;
    public OutputCollector(SedaQueue queue){
        this.queue= queue;
    }
    public void write(String message) {
        try {
            queue.writeOut(message);
        }catch(JMSException ex){
            // do nothing.
        }
    }

    public void write(String message, String outboundQueue) {
        try {
            queue.writeOut(message, outboundQueue);
        }catch(JMSException ex){
            // do nothing.
        }
    }
}
