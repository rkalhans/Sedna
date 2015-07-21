package com.rohitkalhans.sedna.manage.exceptions;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rkalhans on 20-07-2015.
 */
@Slf4j
@Getter
public class SednaException extends RuntimeException {
    private UUID ID;
    private Date occurance;
    public SednaException(String message)
    {
        this(message, null);
    }
    public SednaException(String message, Throwable t )
    {
        super(message, t);
        ID= UUID.randomUUID();
        occurance = new Date();
        StringBuilder builder = new StringBuilder();
        builder.append(message).append(" ExceptionID:").append(ID);
        log.error(builder.toString());
    }
}
