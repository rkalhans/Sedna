package com.rohitkalhans.sedna.manage.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Created by rkalhans on 10-07-2015.
 */
@Getter
public class StageConfig implements Payload {

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

    public StageConfig(ThreadPoolConfig threadPoolConfig,
                       QueueConfig inQueueConfig, QueueConfig outQueueConfig) {
        this.threadPoolConfig = threadPoolConfig;
        this.inQueueConfig = inQueueConfig;
        this.outQueueConfig = outQueueConfig;
    }

    public StageConfig(){}
}
