package com.rohitkalhans.sedna.controllers;

import com.rohitkalhans.sedna.io.OutputCollector;

import javax.jms.Message;

/**
 * Created by rohit.kalhans on 22/12/14.
 */

/**
 * Instance of this class will be created for every event before
 * the dispatcher sends it to the Executor thread-pool.
 */
public class EventHandlerTask implements Runnable {

    /* the output collector which  will be passed to the event handler.*/
    OutputCollector collector;
    /* event which needs to be handled. */
    private Message event;
    /* user defined Handler which will be passed while creating a stage. */
    private EventHandler handler;

    /**
     * Constructor of the eventHandler.
     * @param evt
     * @param handler
     * @param collector
     */
    public EventHandlerTask(Message evt, EventHandler handler, OutputCollector collector) {
        this.event = evt;
        this.handler = handler;
        this.collector = collector;

    }

    /**
     * Calls the the Handler's execute event. It is the responsibility of the execute event
     * to write to the appropriate output channel.
     */
    @Override
    public void run() {
        handler.executeEvent(event, collector);

    }
}
