package com.rohitkalhans.sedna.stage;

import com.rohitkalhans.sedna.io.OutputCollector;

import javax.jms.Message;

/**
 * Created by rohit.kalhans on 21/12/14.
 */
public interface  EventHandler {
    public void executeEvent (Message event, OutputCollector collector);
}
