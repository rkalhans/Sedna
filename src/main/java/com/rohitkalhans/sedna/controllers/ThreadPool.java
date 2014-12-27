package com.rohitkalhans.sedna.controllers;

import com.rohitkalhans.sedna.config.ThreadPoolConfig;
import lombok.Getter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by rohit.kalhans on 21/12/14.
 */

/**
 * Abstraction of thread-pool on top of flexible threadpool
 */
@Getter
public class ThreadPool {

    /* The thread pool */
    private ExecutorService executor;

    /**
     * Constructor of threadpool.
     *
     * @param config
     */
    public ThreadPool(ThreadPoolConfig config) {
        /**
         * The thread pool will be created threads until it reaches
         * the corePoolSize. It means that these number of threads
         * should be sufficient to handle the inflow of events. After
         * that the tasks will be queued. Once the queue is full the
         * executor will start creating new threads. It is kind of
         * balancing. What it essentially means is that the inflow of
         * tasks is more than the processing capacity. So, Executor will
         * start creating new threads again till it reaches Max number
         * of threads. Again, a new threads will be created if and only
         * if the queue is full.
         */
        executor = new ThreadPoolExecutor(config.getCoreSize(),
                config.getMaxSize(),
                config.getIdleTimeOut(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(config.getBlockingQueueSize()));
    }

    /**
     * Submit a new Task to this threadpool. Accepts a new Event-handler task
     *
     * @param eventHandlerTask
     */
    public void submit(EventHandlerTask eventHandlerTask) {
        executor.submit(eventHandlerTask);
    }

    /**
     * Shutdown the threadpool.
     */

    public void shutdown() {
        executor.shutdown();
    }
}
