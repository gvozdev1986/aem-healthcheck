package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Items {

    private Report report;

    private List<Messages> healthCheckMessage;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public List<Messages> getHealthCheckMessage() {
        return healthCheckMessage;
    }

    public void setHealthCheckMessage(List<Messages> healthCheckMessage) {
        this.healthCheckMessage = healthCheckMessage;
    }

}
