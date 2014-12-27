package com.rohitkalhans.sedna.config;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */

/**
 * Parameters to configure the threadpool.
 */

public class ThreadPoolConfig {
    /**
     * Core size. The thread pool will keep growing till this number.
     */
    @JsonProperty
    private int coreSize = 10;

    /**
     * The maximum number of threads that will be spawned by this thread-pool.
     */
    @JsonProperty
    private int maxSize = 100;

    /**
     * Timeout after which threads will be killed if they are idle.
     */
    @JsonProperty
    private int idleTimeOut = 600; // seconds

    /**
     * Blocking queue size. the thread pool size will grow after @coreSize only if this queue is full.
     */
    @JsonProperty
    private int blockingQueueSize = 20;

    public ThreadPoolConfig() {
    }

    @java.beans.ConstructorProperties({"coreSize", "maxSize", "idleTimeOut", "blockingQueueSize"})
    public ThreadPoolConfig(int coreSize, int maxSize, int idleTimeOut, int blockingQueueSize) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.idleTimeOut = idleTimeOut;
        this.blockingQueueSize = blockingQueueSize;
    }

    public int getCoreSize() {
        return this.coreSize;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public int getIdleTimeOut() {
        return this.idleTimeOut;
    }

    public int getBlockingQueueSize() {
        return this.blockingQueueSize;
    }

    ;
}
