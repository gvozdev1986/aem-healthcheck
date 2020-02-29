package com.hvozdzeu.healthcheck.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hvozdzeu.healthcheck.beans.*;
import com.hvozdzeu.healthcheck.constants.HealthCheckConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.hc.api.ResultLog;
import org.apache.sling.hc.api.execution.HealthCheckExecutionOptions;
import org.apache.sling.hc.api.execution.HealthCheckExecutionResult;
import org.apache.sling.hc.api.execution.HealthCheckExecutor;
import org.apache.sling.hc.util.HealthCheckMetadata;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.*;
import static com.hvozdzeu.healthcheck.utils.HealthCheckUtils.*;

@SlingServlet(
        name = "Health Check Executor Servlet",
        methods = {HttpConstants.METHOD_GET},
        paths = {"/system/health"}
)
@Property(name = "sling.auth.requirements", value = "-/system/health")
public class HealthCheckExecutorServlet extends SlingSafeMethodsServlet {

    public static final List<Items> resultList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(HealthCheckExecutorServlet.class);
    private static Map<String, Integer> totalResultMap;

    static {
        totalResultMap = new HashMap<>();
        totalResultMap.put("WARN", 0);
        totalResultMap.put("INFO", 0);
        totalResultMap.put("OK", 0);
        totalResultMap.put("IMPORTANT", 0);
        totalResultMap.put("CRITICAL", 0);
        totalResultMap.put("HEALTH_CHECK_ERROR", 0);
        totalResultMap.put("DEBUG", 0);
    }

    @Reference
    protected HealthCheckExecutor healthCheckExecutor;

    private static List<Messages> getHealthCheckMessageLog(HealthCheckExecutionResult healthCheckResult) {
        List<Messages> messagesList = new ArrayList<>();
        for (ResultLog.Entry entry : getResultList(healthCheckResult.getHealthCheckResult().iterator())) {
            Messages messages = new Messages();
            messages.setLogType(entry.getStatus().toString());
            messages.setMessage(entry.getMessage());
            messagesList.add(messages);
        }
        return messagesList;
    }

    private static Metadata getHealthCheckMetadata(HealthCheckExecutionResult healthCheckResult) {
        HealthCheckMetadata healthCheckMetadata = healthCheckResult.getHealthCheckMetadata();
        Metadata item = new Metadata();
        item.setmBeanName(healthCheckMetadata.getMBeanName());
        item.setTitle(healthCheckMetadata.getTitle());
        item.setServiceId(healthCheckMetadata.getServiceId());
        item.setTags(healthCheckMetadata.getTags());
        item.setAsyncCronExpression(healthCheckMetadata.getAsyncCronExpression());
        return item;
    }

    private static Response generateResponse(List<HealthCheckExecutionResult> executionResults) {
        List<Items> checkResultList = new ArrayList<>();
        int i = 0;
        for (HealthCheckExecutionResult healthCheckResult : executionResults) {
            Items checkResult = new Items();
            Report report = new Report();
            report.setName(healthCheckResult.getHealthCheckMetadata() != null ? healthCheckResult.getHealthCheckMetadata().getName() : "");
            String status = healthCheckResult.getHealthCheckResult().getStatus().toString();
            report.setStatus(status);
            report.setElapsedTimeInMs(healthCheckResult.getElapsedTimeInMs());
            report.setMetadata(getHealthCheckMetadata(healthCheckResult));
            report.setMessages(getHealthCheckMessageLog(healthCheckResult));
            report.setFaIcon(buildFaIcons(status));
            buildMapCountStatus(totalResultMap, healthCheckResult.getHealthCheckResult().getStatus().toString());
            checkResult.setReport(report);
            checkResultList.add(checkResult);
            i++;
            report.setMessagesUrl(buildMessagesUrl(i));
        }
        resultList.addAll(checkResultList);
        return getResponse(checkResultList);
    }

    private static Response getResponse(List<Items> checkResultList) {
        Response response = new Response();
        response.setItems(checkResultList);
        response.setTotal(buildTotalResult(totalResultMap));
        return response;
    }

    @Activate
    protected void activate(ComponentContext context) {
        logger.debug("Starting HealthCheckExecutorServlet");
    }

    @Deactivate
    protected void deactivate() {
        logger.debug("Stopping HealthCheckExecutorServlet");
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws IOException {

        initTotalMap(totalResultMap);

        response.setContentType(HealthCheckConstants.APPLICATION_JSON);
        response.setHeader(HealthCheckConstants.CACHE_CONTROL, HealthCheckConstants.CACHE_CONTROL_VALUE);

        String tagsStr = StringUtils.defaultString(request.getParameter(HealthCheckConstants.PARAM_TAGS));
        String[] tags = tagsStr.split(HealthCheckConstants.REGEX_TAG_FORMAT);
        String combineTagsOr = StringUtils.defaultString(request.getParameter(HealthCheckConstants.PARAM_COMBINE_TAGS_OR), "true");

        HealthCheckExecutionOptions options = new HealthCheckExecutionOptions();
        options.setCombineTagsWithOr(Boolean.parseBoolean(combineTagsOr));
        options.setForceInstantExecution(true);
        List<HealthCheckExecutionResult> results = healthCheckExecutor.execute(options, tags);

        response.setStatus(HttpServletResponse.SC_OK);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String json = mapper.writeValueAsString(generateResponse(results));
        response.getWriter().write(StringUtils.isNotEmpty(json) ? json : ERROR_CREATE_REPORT);
    }

}
