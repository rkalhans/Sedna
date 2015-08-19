package com.rohitkalhans.sedna.manage.server;

import com.rohitkalhans.sedna.manage.payloads.Payload;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by rkalhans on 14-06-2015.
 */

@Getter
public class ManagementConfig extends Configuration implements Payload {

    @NotNull
    @JsonProperty
    private String stageJarPath;

    @JsonProperty
    private String additionalJarPath;

    @JsonProperty
    private String workingDir;

}