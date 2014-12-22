package com.rohitkalhans.sedna.config;

import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */
@Getter
public class StageConfig {

    @JsonProperty
    QueueConfig queueConfig;

    @JsonProperty
    ThreadPoolConfig threadPoolConfig;
}
