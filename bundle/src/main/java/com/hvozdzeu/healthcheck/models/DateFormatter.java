package com.hvozdzeu.healthcheck.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Model(adaptables = SlingHttpServletRequest.class)
public class DateFormatter {

    public String value;
    @Inject
    private Calendar date;
    @Inject
    private String format;

    @PostConstruct
    protected void init() {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        value = formatter.format(date.getTime());
    }
}