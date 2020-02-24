package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    private HealthCheckMetadata healthCheckMetadata;
    private List<String> messageLog;
    private String name;
    private String status;
    private long timeMs;

    public HealthCheckMetadata getHealthCheckMetadata() {
        return healthCheckMetadata;
    }

    public void setHealthCheckMetadata(HealthCheckMetadata healthCheckMetadata) {
        this.healthCheckMetadata = healthCheckMetadata;
    }

    public List<String> getMessageLog() {
        return messageLog;
    }

    public void setMessageLog(List<String> messageLog) {
        this.messageLog = messageLog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public void setTimeMs(long timeMs) {
        this.timeMs = timeMs;
    }

    @Override
    public String toString() {
        return "Result{" +
                "healthCheckMetadata=" + healthCheckMetadata +
                ", messageLog=" + messageLog +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", timeMs=" + timeMs +
                '}';
    }
}
