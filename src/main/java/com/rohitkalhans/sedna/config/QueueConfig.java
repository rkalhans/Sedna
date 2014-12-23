package com.rohitkalhans.sedna.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by rohit.kalhans on 22/12/14.
 */
@Getter
@AllArgsConstructor
public class QueueConfig {

    @JsonProperty
    private int queuePort=2166;
    public QueueConfig(){}
}
