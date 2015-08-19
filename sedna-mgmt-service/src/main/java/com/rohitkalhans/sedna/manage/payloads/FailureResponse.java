package com.rohitkalhans.sedna.manage.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rohitkalhans.sedna.manage.exceptions.SednaException;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rkalhans on 20-07-2015.
 */
@Getter
public class FailureResponse implements Payload {

    @JsonProperty
    private  String message;

    @JsonProperty
    private Date occurence;

    @JsonProperty
    private String errorID;

    private FailureResponse(){}

    public static FailureResponse from(SednaException sednaException) {
        FailureResponse fr = new FailureResponse();
        fr.message = sednaException.getMessage();
        fr.occurence = sednaException.getOccurance();
        fr.errorID = sednaException.getID().toString();
        return fr;
    }
}
