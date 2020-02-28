package com.hvozdzeu.healthcheck.models;

import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.ERROR_CREATE_REPORT;
import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.HTTP_PARAM_REPORT_ID;
import static com.hvozdzeu.healthcheck.servlets.HealthCheckExecutorServlet.resultList;
import static com.hvozdzeu.healthcheck.utils.HealthCheckUtils.buildBackUrl;
import static com.hvozdzeu.healthcheck.utils.HealthCheckUtils.buildMapCountStatus;
import static com.hvozdzeu.healthcheck.utils.HealthCheckUtils.initTotalMap;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hvozdzeu.healthcheck.beans.Messages;
import com.hvozdzeu.healthcheck.beans.Metadata;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

@Model(
    adaptables = {SlingHttpServletRequest.class, Resource.class},
    resourceType = {"healthcheck/components/content/report"},
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json", selector = "report", options = {
    @ExporterOption(name = "MapperFeature.SORT_PROPERTIES_ALPHABETICALLY", value = "true"),
    @ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "false")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessagesReportModel {

    @Self
    private SlingHttpServletRequest request;

    private Metadata metadata;
    @JsonIgnore
    private Integer reportId;
    private String message;
    private List<Messages> messagesList;
    private String backUrl;
    private Map<String, Integer> totalTypes;


    @PostConstruct
    private void init() {
        reportId = Integer.valueOf(request.getParameter(HTTP_PARAM_REPORT_ID));
        messagesList = new ArrayList<>();
        if (!resultList.isEmpty()) {
            metadata = resultList.get(reportId - 1).getReport().getMetadata();
            messagesList = resultList.get(reportId - 1).getReport().getMessages();
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

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public String getMessage() {
        return message;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public Map<String, Integer> getTotalTypes() {
        return totalTypes;
    }
}
