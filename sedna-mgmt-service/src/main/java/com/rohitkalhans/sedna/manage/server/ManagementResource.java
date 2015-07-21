package com.rohitkalhans.sedna.manage.server;

import com.codahale.metrics.annotation.Timed;
import com.rohitkalhans.sedna.manage.controller.Host;
import com.rohitkalhans.sedna.manage.exceptions.SednaException;
import com.rohitkalhans.sedna.manage.payloads.JVMOpts;
import com.rohitkalhans.sedna.manage.payloads.StageConfig;
import com.rohitkalhans.sedna.manage.payloads.SuccessResponse;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by rkalhans on 09-07-2015.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class ManagementResource {

    private ManagementConfig config;
    private Host host;
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
}
