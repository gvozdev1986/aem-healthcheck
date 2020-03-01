package com.hvozdzeu.healthcheck.models;

import com.adobe.cq.sightly.WCMUsePojo;
import com.hvozdzeu.healthcheck.beans.Messages;
import com.hvozdzeu.healthcheck.beans.Metadata;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.ERROR_CREATE_REPORT;
import static com.hvozdzeu.healthcheck.servlets.HealthCheckExecutorServlet.resultList;
import static com.hvozdzeu.healthcheck.utils.HealthCheckUtils.*;

@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class MessagesReportComponentModel extends WCMUsePojo {

    private Metadata metadata;
    private String reportId;
    private String message;
    private List<Messages> messagesList;
    private String backUrl;
    private Map<String, Integer> totalTypes;

    @Override
    public void activate() {
        reportId = "3";//get("reportId", String.class);
        System.out.println(reportId);
        getReportById();
    }

    private void getReportById() {
        messagesList = new ArrayList<>();
        if (!resultList.isEmpty()) {
            metadata = resultList.get(Integer.parseInt(reportId) - 1).getReport().getMetadata();
            messagesList = resultList.get(Integer.parseInt(reportId) - 1).getReport().getMessages();
            backUrl = buildBackUrl();
            buildTotalTypes();
        } else {
            message = ERROR_CREATE_REPORT;
        }
    }

    private void buildTotalTypes() {
        totalTypes = new HashMap<>();
        initTotalMap(totalTypes);
        for (Messages messages : messagesList) {
            buildMapCountStatus(totalTypes, messages.getLogType());
        }
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public Map<String, Integer> getTotalTypes() {
        return totalTypes;
    }

    public void setTotalTypes(Map<String, Integer> totalTypes) {
        this.totalTypes = totalTypes;
    }

}
