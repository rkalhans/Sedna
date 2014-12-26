package com.rohitkalhans.sedna.io;

import javax.jms.JMSException;

/**
 * Created by rohit.kalhans on 23/12/14.
 */

/**
 * Collector class which will be available to the event handler tasks.
 * The collectors can write to any queue based on the requirement.
 */
public class OutputCollector {

    /* The queue to which the collector can write to. */
    private SedaQueue queue;

    /**
     * Constructor to instantiate the Output collector.
     *
     * @param queue
     */
    public OutputCollector(SedaQueue queue) {
        this.queue = queue;
    }

    /**
     * write to default output queue.
     *
     * @param message
     */
    public void write(String message) {
        try {
            queue.writeOut(message);
        } catch (JMSException ex) {
            // do nothing.
        }
    }

    /**
     * Write to a custom(named) Queue.
     *
     * @param message
     * @param outboundQueue Name of outbound queue.
     */
    public void write(String message, String outboundQueue) {
        try {
            queue.writeOut(message, outboundQueue);
        } catch (JMSException ex) {
            // do nothing.
        }
    }
}
