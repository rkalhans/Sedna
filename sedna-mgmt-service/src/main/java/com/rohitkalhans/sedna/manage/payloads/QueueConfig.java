package com.rohitkalhans.sedna.manage.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by rkalhans on 10-07-2015.
 */
@AllArgsConstructor
@Getter
public class QueueConfig implements Payload {

    /* The port on which the ActiveMQ broker will be started. */
    @JsonProperty
    private int queuePort = 2166;

    @JsonProperty
    private String host= "localhost";

    @JsonProperty
    private String queueName;

    public QueueConfig() {
    }
}
