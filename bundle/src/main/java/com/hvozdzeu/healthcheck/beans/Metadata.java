package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata {

    private String mBeanName;
    private String title;
    private Long serviceId;
    private List<String> tags;
    private String asyncCronExpression;

    public String getmBeanName() {
        return mBeanName;
    }

    public void setmBeanName(String mBeanName) {
        this.mBeanName = mBeanName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAsyncCronExpression() {
        return asyncCronExpression;
    }

    public void setAsyncCronExpression(String asyncCronExpression) {
        this.asyncCronExpression = asyncCronExpression;
    }

}
