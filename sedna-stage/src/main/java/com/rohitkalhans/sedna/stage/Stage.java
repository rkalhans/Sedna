package com.rohitkalhans.sedna.stage;

import com.rohitkalhans.sedna.config.StageConfig;
import com.rohitkalhans.sedna.controllers.EventHandler;
import com.rohitkalhans.sedna.io.SedaQueue;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the first prototype towards creating a new SEDA based wire-frame which
 * can be extended to the create various even driven application.
 * Essential part of any staged event driven architecture is stage which will be the
 * building block of our application. We will end up extending abstract stage with various
 * functionality as per our requirement.
 * <p/>
 * A stage is essentially a self-sufficient and the smallest independently functional
 * unit of seda. It has an input and output both of which are being done via a message queue.
 * Essentially within a stage we will read events from inbound queue, and will write to an
 * outbound queue, subscribers of which will then process the events as and when they are emitted
 * by the current stage. Moreover there is a stage controller which will control the functioning of the
 * stage and will handle the batch size and the thread pool size,(to maximize the throughput of the stage
 * and to minimize the latency.) The final and the most essential part of any stage is the event
 * handler which will hold the event processing logic.
 */
@Getter
@Setter
public class Stage implements Lifecycle {
    private final SedaQueue queue;
    private final EventDispatcher dispatcher;
    private String name;

    public Stage(StageConfig config, EventHandler handler) {

        //initialize the Queue.
        queue = new SedaQueue(config.getInQueueConfig(), config.getOutQueueConfig(), config.isNeedQueue());

        // initialize the dispatcher
        dispatcher = new EventDispatcher(config.getThreadPoolConfig(), handler, queue);

    }

    /**
     * Start a stage. It has two basic functionality,
     * 1. Start the Queue
     * 2. Register the dispatcher with this queue.
     * Irrespective to weather the Queue was started by the this stage or any other,
     * the dispatcher will be registered to the source queue.
     */

    @Override
    public void start() {

        Runtime.getRuntime().addShutdownHook(new StageCleaner());
        // let the dispatcher start listening to the messages on the queue.
        queue.start();
        queue.registerListener(dispatcher);
    }

    /**
     * No-op as of now. We should be able to pause the stage at a safe point.
     * By safe point I mean when all the scheduled events have been processed and we
     * have safely stored (persisted) the current state, so that it is not volatile.
     */
    @Override
    public void pause() {

    }

    /**
     * Stops the stage. The queue is stopped and the dispatcher is stopped and the
     * dispatcher's threadpool is shutdown.
     */

    @Override
    public void stop() {
        queue.stop();
        dispatcher.stop();
    }

}
