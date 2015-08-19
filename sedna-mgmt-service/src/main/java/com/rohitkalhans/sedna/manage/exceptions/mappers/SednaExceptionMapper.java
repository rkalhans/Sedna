package com.rohitkalhans.sedna.manage.exceptions.mappers;

import com.rohitkalhans.sedna.manage.exceptions.SednaException;
import com.rohitkalhans.sedna.manage.payloads.FailureResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by rkalhans on 20-07-2015.
 */
@Provider
public class SednaExceptionMapper implements ExceptionMapper<SednaException> {
    @Override
    public Response toResponse(SednaException sednaException) {
        FailureResponse failure= FailureResponse.from(sednaException);
        return Response.status(400).entity(failure).build();
    }
}
