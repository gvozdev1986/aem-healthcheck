
package com.hvozdzeu.healthcheck.hc.impl;

import java.util.Date;
import org.apache.sling.hc.annotations.SlingHealthCheck;
import org.apache.sling.hc.api.HealthCheck;
import org.apache.sling.hc.api.Result;
import org.apache.sling.hc.util.FormattingResultLog;

@SlingHealthCheck(
    name = "Smoke Health Check",
    mbeanName = "smokeHC",
    description = "This health check determines if an instance is ready to serve requests",
    tags = {"shallow", "devops"}
)
public class SmokeHealthCheck implements HealthCheck {

    @Override
    public Result execute() {
        FormattingResultLog resultLog = new FormattingResultLog();
        resultLog.info("Instance is ready at {}", new Date());
        return new Result(resultLog);
    }
}
