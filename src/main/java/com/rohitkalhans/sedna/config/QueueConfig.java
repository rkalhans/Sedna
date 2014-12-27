package com.rohitkalhans.sedna.config;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */

/**
 * Config to handle to the Queue.
 */
public class QueueConfig {

    /* The port on which the ActiveMQ broker will be started. */
    @JsonProperty
    private int queuePort = 2166;

    /**
     * default constructor.
     */
    public QueueConfig() {
    }

    @java.beans.ConstructorProperties({"queuePort"})
    public QueueConfig(int queuePort) {
        this.queuePort = queuePort;
    }

    public int getQueuePort() {
        return this.queuePort;
    }
}
