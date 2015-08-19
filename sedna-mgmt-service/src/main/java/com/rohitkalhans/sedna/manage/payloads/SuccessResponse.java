package com.rohitkalhans.sedna.manage.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Created by rkalhans on 10-07-2015.
 */
public class SuccessResponse{

    @JsonProperty
    private Payload config;

    @JsonProperty
    private String status;

    public static SuccessResponse from(Payload config){
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.config= config;
        successResponse.status = "SUCCESS";
        return successResponse;
    }
}