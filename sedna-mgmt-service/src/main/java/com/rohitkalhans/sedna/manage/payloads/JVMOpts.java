package com.rohitkalhans.sedna.manage.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * Created by rkalhans on 19-07-2015.
 */
@Getter
public class JVMOpts implements Payload {

    @JsonProperty
    String maxHeap;

    @JsonProperty
    String initialHeap;

    @JsonProperty
    String threadStackSize;
}
