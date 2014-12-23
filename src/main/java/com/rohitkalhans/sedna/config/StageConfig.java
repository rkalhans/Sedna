package com.rohitkalhans.sedna.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */
@Getter
@AllArgsConstructor
public class StageConfig {

    @JsonProperty
    QueueConfig queueConfig;

    @JsonProperty
    ThreadPoolConfig threadPoolConfig;
}
