package com.rohitkalhans.sedna.manage.server;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohitkalhans.sedna.manage.controller.Host;
import com.rohitkalhans.sedna.manage.exceptions.SednaException;
import com.rohitkalhans.sedna.manage.payloads.JVMOpts;
import com.rohitkalhans.sedna.manage.payloads.StageConfig;
import com.rohitkalhans.sedna.manage.payloads.SuccessResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by rkalhans on 09-07-2015.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ManagementResource {

    private ManagementConfig config;
    public static Host host;
    ManagementResource(ManagementConfig config){
        this.config = config;
    }


    @PUT
    @Timed
    @Path("/buildHost")
    public SuccessResponse buildHost(JVMOpts JVMSize,
                                     @QueryParam("slots") int slots)
    {
        this.host = new Host(JVMSize, slots, config);
        return SuccessResponse.from(null);
    }

    @PUT
    @Timed
    @Path("/addSlot")
    public SuccessResponse addSlot(StageConfig config)
    {
        try {
            host.addSlot(config);
        }catch (NullPointerException npe){
            throw new SednaException("No host configured on this machine. use buildHost endpoint first.", npe);
        }
        return SuccessResponse.from(config);
    }

    @POST
    @Timed
    @Path("/switchSlot")
    public SuccessResponse switchSlot(@QueryParam("victim")String victim, StageConfig newStageConfig)
    {
        host.switchSlot(victim,newStageConfig);
         return SuccessResponse.from(newStageConfig);
    }

    @GET
    @Timed
    @Path("/hostStatus")
    public Host getStatus() throws JsonProcessingException {
        String hostJson = new ObjectMapper().writeValueAsString(host);
        return host;
    }

}
