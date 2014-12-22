package com.rohitkalhans.sedna.controllers;

import com.rohitkalhans.sedna.io.OutputCollector;
import com.rohitkalhans.sedna.stage.EventHandler;

import javax.jms.Message;

/**
 * Created by rohit.kalhans on 22/12/14.
 */
public class EventHandlerTask implements Runnable {

    private Message event;
    private EventHandler handler;
    OutputCollector collector;
    public EventHandlerTask(Message  evt, EventHandler handler, OutputCollector collector)
    {
        this.event= evt;
        this.handler= handler;
        this.collector= collector;

    }
    @Override
    public void run() {
        handler.executeEvent(event,collector);

    }
}
