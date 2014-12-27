package com.rohitkalhans.sedna.stage;

import com.rohitkalhans.sedna.config.ThreadPoolConfig;
import com.rohitkalhans.sedna.controllers.EventHandler;
import com.rohitkalhans.sedna.controllers.EventHandlerTask;
import com.rohitkalhans.sedna.controllers.ThreadPool;
import com.rohitkalhans.sedna.io.OutputCollector;
import com.rohitkalhans.sedna.io.SedaQueue;

import javax.jms.Message;
import javax.jms.MessageListener;


/**
 * Created by rohit.kalhans on 22/12/14.
 */

/**
 * Standard Event dispatcher implementation. The event dispatcher listens to the
 * the source queue and waits for events. For every event seen by it, it creates a Runnable
 * instance using the EventHandlerTask and submits it to the thread-pool.
 */
public class EventDispatcher implements MessageListener {

    /* The threadpool used to handle the events.*/
    private ThreadPool threadPool;

    /* Provides the core implementation of the logic to handle an event.*/
    private EventHandler handler;

    /* Queue that will be used for IO. may or may not have been started by the current stage.*/
    private SedaQueue queue;

    /* Collector to hold the processed events. */
    private OutputCollector collector;

    /**
     * Constructor to initialize the event dispatcher.
     *
     * @param config  Config of the thread pool.
     * @param handler Event handler logic. Should ideally be provided while initializing the Stage.
     * @param queue   The Source and the output queue. We will be using ActiveMQ which should have saperate virtual queues for
     *                input and output handling. see @link SedaQueue for more details.
     */
    EventDispatcher(ThreadPoolConfig config, EventHandler handler, SedaQueue queue) {
        threadPool = new ThreadPool(config);
        this.handler = handler;
        this.queue = queue;
        collector = new OutputCollector(queue);
    }

    /**
     * The callback function that is called when an event is received by the input queue.
     *
     * @param message The event will be passed to this method as Jmx message.
     */

    @Override
    public void onMessage(Message message) {
        threadPool.submit(new EventHandlerTask(message, handler, collector));
    }

    /**
     * Stops the Dispatcher. The threadpool will be shutdown.
     */

    public boolean stop() {
        threadPool.shutdown();
        return true;
    }
}
