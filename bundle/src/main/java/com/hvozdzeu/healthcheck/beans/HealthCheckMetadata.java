package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthCheckMetadata {

    private Object asyncCronExpression;
    private String mBeanName;
    private long serviceId;
    private String tags;
    private String title;

    public Object getAsyncCronExpression() {
        return asyncCronExpression;
    }

    public void setAsyncCronExpression(Object asyncCronExpression) {
        this.asyncCronExpression = asyncCronExpression;
    }

    public String getmBeanName() {
        return mBeanName;
    }

    public void setmBeanName(String mBeanName) {
        this.mBeanName = mBeanName;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "HealthCheckMetadata{" +
                "asyncCronExpression=" + asyncCronExpression +
                ", mBeanName='" + mBeanName + '\'' +
                ", serviceId=" + serviceId +
                ", tags='" + tags + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
