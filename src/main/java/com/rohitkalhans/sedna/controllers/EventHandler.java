package com.rohitkalhans.sedna.controllers;

import com.rohitkalhans.sedna.io.OutputCollector;

import javax.jms.Message;

/**
 * Created by rohit.kalhans on 21/12/14.
 */

/**
 * Abstract Event handler class. Provides core logic to handle the events.
 */
public interface EventHandler {
    /**
     * This method should be overridden with the custom logic of handling the event.
     *
     * @param event Event that needs to be handled.
     * @param collector Output collector which can be used to write to the output queue.
     */
    public abstract void executeEvent(Message event, OutputCollector collector);
}
