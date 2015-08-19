package com.rohitkalhans.sedna.manage.server;

import com.rohitkalhans.sedna.manage.exceptions.mappers.SednaExceptionMapper;
import com.rohitkalhans.sedna.manage.heath.ManagementHeathCheck;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

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
        configureCors(environment);
        ManagementResource resource = new ManagementResource(managementConfig);
        environment.jersey().register(resource);
        environment.healthChecks().register("SEDNA", new ManagementHeathCheck());
        environment.jersey().register(new SednaExceptionMapper());
        Thread t = new Thread(new ElasticResourceManager());
        t.start();
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }

}