package com.rohitkalhans.sedna.manage.heath;

import com.codahale.metrics.health.HealthCheck;

public class ManagementHeathCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}