package com.rohitkalhans.sedna.stage;

import com.rohitkalhans.sedna.io.OutputCollector;
import com.rohitkalhans.sedna.controllers.EventHandlerTask;
import com.rohitkalhans.sedna.config.ThreadPoolConfig;
import com.rohitkalhans.sedna.controllers.ThreadPool;
import com.rohitkalhans.sedna.io.SedaQueue;

import javax.jms.Message;
import javax.jms.MessageListener;


/**
 * Created by rohit.kalhans on 22/12/14.
 */
public class EventDispatcher implements MessageListener {
    private ThreadPool threadPool;
    private EventHandler handler;
    private SedaQueue queue;
    private OutputCollector collector;
    EventDispatcher(ThreadPoolConfig config, EventHandler handler, SedaQueue queue)
    {
        threadPool= new ThreadPool(config);
        this.handler= handler;
        this.queue= queue;
        collector= new OutputCollector(queue);
    }


    @Override
    public void onMessage(Message message) {
        threadPool.submit(new EventHandlerTask(message, handler, collector));
    }

    public boolean  stop()
    {
        threadPool.shutdown();
        return true;
    }
}
