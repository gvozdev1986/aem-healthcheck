package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Total {

    private Integer totalItems;
    private Integer warn;
    private Integer info;
    private Integer ok;
    private Integer important;
    private Integer critical;
    private Integer healthCheckError;
    private Integer debug;

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public Integer getWarn() {
        return warn;
    }

    public void setWarn(Integer warn) {
        this.warn = warn;
    }

    public Integer getInfo() {
        return info;
    }

    public void setInfo(Integer info) {
        this.info = info;
    }

    public Integer getOk() {
        return ok;
    }

    public void setOk(Integer ok) {
        this.ok = ok;
    }

    public Integer getImportant() {
        return important;
    }

    public void setImportant(Integer important) {
        this.important = important;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }

    public Integer getHealthCheckError() {
        return healthCheckError;
    }

    public void setHealthCheckError(Integer healthCheckError) {
        this.healthCheckError = healthCheckError;
    }

    public Integer getDebug() {
        return debug;
    }

    public void setDebug(Integer debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return "Total{" +
            "totalItems=" + totalItems +
            ", warn=" + warn +
            ", info=" + info +
            ", ok=" + ok +
            ", important=" + important +
            ", critical=" + critical +
            ", healthCheckError=" + healthCheckError +
            ", debug=" + debug +
            '}';
    }
}
