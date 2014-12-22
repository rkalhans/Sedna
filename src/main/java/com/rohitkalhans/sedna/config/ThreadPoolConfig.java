package com.rohitkalhans.sedna.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */
@Getter
@AllArgsConstructor
public class ThreadPoolConfig {
    @JsonProperty
    private int coreSize= 10;

    @JsonProperty
    private int maxSize= 100;

    @JsonProperty
    private int idleTimeOut= 600; // seconds

    @JsonProperty
    private int blockingQueueSize= 20;
}
