package com.rohitkalhans.sedna.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */

/**
 * Config for a given stage
 */
@Getter
@AllArgsConstructor
public class StageConfig {

    @JsonProperty
    String host;

    @JsonProperty
    String sourceStage;

    @JsonProperty
    private String name="DefaultStage";

    @JsonProperty
    private boolean needQueue= false;

    @JsonProperty
    private QueueConfig inQueueConfig;

    @JsonProperty
    private QueueConfig outQueueConfig;

    @JsonProperty
    private String eventHandler;

    /* Config for threadpool. */
    @JsonProperty
    private ThreadPoolConfig threadPoolConfig;

    public StageConfig (ThreadPoolConfig threadPoolConfig,
                             QueueConfig inQueueConfig, QueueConfig outQueueConfig) {
        this.threadPoolConfig = threadPoolConfig;
        this.inQueueConfig = inQueueConfig;
        this.outQueueConfig = outQueueConfig;
    }

    public StageConfig(){}
}
