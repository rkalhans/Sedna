package com.rohitkalhans.sedna.config;

import lombok.AllArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */

/**
 * Config to handle to the Queue.
 */
@AllArgsConstructor
public class QueueConfig {

    /* The port on which the ActiveMQ broker will be started. */
    @JsonProperty
    private int queuePort = 2166;

    /**
     * default constructor.
     */
    public QueueConfig() {
    }

    public int getQueuePort() {
        return this.queuePort;
    }
}
