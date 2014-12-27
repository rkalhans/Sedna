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

    /* host on which the stage is deployed. */
    @JsonProperty
    String host;
    /* Source stage which is the upstream to this stage. */
    @JsonProperty
    String sourceStage;
    /* Name of the stage. */
    @JsonProperty
    private String name;
    /* Queue config which will provide the config parameters for the ActiveMQ broker */
    @JsonProperty
    private QueueConfig queueConfig;

    /* Config for threadpool. */
    @JsonProperty
    private ThreadPoolConfig threadPoolConfig;

    public StageConfig(ThreadPoolConfig threadPoolConfig, QueueConfig queueConfig) {
        this.threadPoolConfig = threadPoolConfig;
        this.queueConfig = queueConfig;
    }

}
