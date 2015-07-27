package com.rohitkalhans.sedna.manage.server;

import com.rohitkalhans.sedna.manage.exceptions.mappers.SednaExceptionMapper;
import com.rohitkalhans.sedna.manage.heath.ManagementHeathCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by rkalhans on 09-07-2015.
 */
public class SednaMgmt extends Application<ManagementConfig> {

    public static void main(String[] args) throws Exception {
        new SednaMgmt().run(args);

    }

    @Override
    public void initialize(Bootstrap<ManagementConfig> bootstrap) {
    }

    @Override
    public void run(ManagementConfig managementConfig, Environment environment) throws Exception {
        ManagementResource resource = new ManagementResource(managementConfig);
        environment.jersey().register(resource);
        environment.healthChecks().register("SEDNA", new ManagementHeathCheck());
        environment.jersey().register(new SednaExceptionMapper());
    }

}