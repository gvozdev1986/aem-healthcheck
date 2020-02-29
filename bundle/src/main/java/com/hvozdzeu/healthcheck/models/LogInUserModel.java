package com.hvozdzeu.healthcheck.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import javax.jcr.Session;

@Model(adaptables = SlingHttpServletRequest.class)
public class LogInUserModel {

    public String user;
    @SlingObject
    private ResourceResolver resourceResolver;

    @PostConstruct
    protected void init() {
        Session session = resourceResolver.adaptTo(Session.class);
        if (session != null) {
            user = session.getUserID();
        }
    }

    public String getUser() {
        return user;
    }
}