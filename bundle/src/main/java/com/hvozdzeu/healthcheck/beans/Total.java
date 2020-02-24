package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Total {

    private long critical;
    private long healCheckError;
    private long important;
    private long info;
    private long ok;
    private long total;
    private long warn;

    public long getCritical() {
        return critical;
    }

    public void setCritical(long critical) {
        this.critical = critical;
    }

    public long getHealCheckError() {
        return healCheckError;
    }

    public void setHealCheckError(long healCheckError) {
        this.healCheckError = healCheckError;
    }

    public long getImportant() {
        return important;
    }

    public void setImportant(long important) {
        this.important = important;
    }

    public long getInfo() {
        return info;
    }

    public void setInfo(long info) {
        this.info = info;
    }

    public long getOk() {
        return ok;
    }

    public void setOk(long ok) {
        this.ok = ok;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getWarn() {
        return warn;
    }

    public void setWarn(long warn) {
        this.warn = warn;
    }

    @Override
    public String toString() {
        return "Total{" +
                "critical=" + critical +
                ", healCheckError=" + healCheckError +
                ", important=" + important +
                ", info=" + info +
                ", ok=" + ok +
                ", total=" + total +
                ", warn=" + warn +
                '}';
    }
}
