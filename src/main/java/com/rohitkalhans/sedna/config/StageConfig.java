package com.rohitkalhans.sedna.config;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */

/**
 * Config for a given stage
 */
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

    @java.beans.ConstructorProperties({"host", "sourceStage", "name", "queueConfig", "threadPoolConfig"})
    public StageConfig(String host, String sourceStage, String name, QueueConfig queueConfig, ThreadPoolConfig threadPoolConfig) {
        this.host = host;
        this.sourceStage = sourceStage;
        this.name = name;
        this.queueConfig = queueConfig;
        this.threadPoolConfig = threadPoolConfig;
    }

    public String getHost() {
        return this.host;
    }

    public String getSourceStage() {
        return this.sourceStage;
    }

    public String getName() {
        return this.name;
    }

    public QueueConfig getQueueConfig() {
        return this.queueConfig;
    }

    public ThreadPoolConfig getThreadPoolConfig() {
        return this.threadPoolConfig;
    }
}
