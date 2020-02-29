package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LangProperties {

    private String lblDate;
    private String lblHcVersion;
    private String lblInstInformation;
    private String lblInstVersion;
    private String lblUserId;

    public String getLblDate() {
        return lblDate;
    }

    public void setLblDate(String lblDate) {
        this.lblDate = lblDate;
    }

    public String getLblHcVersion() {
        return lblHcVersion;
    }

    public void setLblHcVersion(String lblHcVersion) {
        this.lblHcVersion = lblHcVersion;
    }

    public String getLblInstInformation() {
        return lblInstInformation;
    }

    public void setLblInstInformation(String lblInstInformation) {
        this.lblInstInformation = lblInstInformation;
    }

    public String getLblInstVersion() {
        return lblInstVersion;
    }

    public void setLblInstVersion(String lblInstVersion) {
        this.lblInstVersion = lblInstVersion;
    }

    public String getLblUserId() {
        return lblUserId;
    }

    public void setLblUserId(String lblUserId) {
        this.lblUserId = lblUserId;
    }
}
