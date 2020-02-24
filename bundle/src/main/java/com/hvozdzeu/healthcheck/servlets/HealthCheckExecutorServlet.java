package com.hvozdzeu.healthcheck.servlets;

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
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
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

import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.JSON_PARAM_MESSAGE_LOG;
import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.MESSAGE_LOG_FORMAT;
import static com.hvozdzeu.healthcheck.utils.HealthCheckUtils.*;

@SlingServlet(
        name = "Health Check Executor Servlet",
        generateComponent = true,
        methods = {HttpConstants.METHOD_GET},
        paths = {"/system/health"}
)
@Property(name = "sling.auth.requirements", value = "-/system/health")
public class HealthCheckExecutorServlet extends SlingSafeMethodsServlet {

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
    }

    @Reference
    protected HealthCheckExecutor healthCheckExecutor;

    private static void generateResponse(List<HealthCheckExecutionResult> executionResults, JSONObject resultJson) throws JSONException {
        JSONArray resultsJsonArr = new JSONArray();
        resultJson.put(HealthCheckConstants.JSON_PARAM_RESULTS, resultsJsonArr);

        for (HealthCheckExecutionResult healthCheckResult : executionResults) {
            JSONObject result = new JSONObject();
            result.put(HealthCheckConstants.JSON_PARAM_NAME, healthCheckResult.getHealthCheckMetadata() != null ? healthCheckResult.getHealthCheckMetadata().getName() : "");

            String status = healthCheckResult.getHealthCheckResult().getStatus().toString();
            result.put(HealthCheckConstants.JSON_PARAM_STATUS, status);
            buildMapCountStatus(totalResultMap, status);

            result.put(HealthCheckConstants.JSON_PARAM_TIME_MS, healthCheckResult.getElapsedTimeInMs());
            getHealthCheckMessageLog(healthCheckResult, result);
            getHealthCheckMetadata(healthCheckResult, result);
            resultsJsonArr.put(result);
        }
        resultJson.put(HealthCheckConstants.JSON_PARAM_TOTAL, buildTotalResult(totalResultMap));
    }


    private static void getHealthCheckMessageLog(HealthCheckExecutionResult healthCheckResult, JSONObject result) throws JSONException {
        List<String> messages = new ArrayList<>();
        for (ResultLog.Entry entry : getResultList(healthCheckResult.getHealthCheckResult().iterator())) {
            messages.add(String.format(MESSAGE_LOG_FORMAT, entry.getStatus().toString(), entry.getMessage()));
        }
        result.put(JSON_PARAM_MESSAGE_LOG, messages);
    }

    private static void getHealthCheckMetadata(HealthCheckExecutionResult healthCheckResult, JSONObject result) throws JSONException {
        HealthCheckMetadata healthCheckMetadata = healthCheckResult.getHealthCheckMetadata();
        Map<String, Object> map = new HashMap<>();
        map.put(HealthCheckConstants.JSON_PARAM_M_BEAN_NAME, healthCheckMetadata.getMBeanName());
        map.put(HealthCheckConstants.JSON_PARAM_TITLE, healthCheckMetadata.getTitle());
        map.put(HealthCheckConstants.JSON_PARAM_SERVICE_ID, healthCheckMetadata.getServiceId());
        map.put(HealthCheckConstants.JSON_PARAM_TAGS, healthCheckMetadata.getTags());
        map.put(HealthCheckConstants.JSON_PARAM_ASYNC_CRON_EXPRESSION, healthCheckMetadata.getAsyncCronExpression());
        result.put(HealthCheckConstants.JSON_PARAM_HEALTH_CHECK_METADATA, map);
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
        response.setContentType(HealthCheckConstants.APPLICATION_JSON);
        response.setHeader(HealthCheckConstants.CACHE_CONTROL, HealthCheckConstants.CACHE_CONTROL_VALUE);

        String tagsStr = StringUtils.defaultString(request.getParameter(HealthCheckConstants.PARAM_TAGS));
        String[] tags = tagsStr.split(HealthCheckConstants.REGEX_TAG_FORMAT);
        String combineTagsOr = StringUtils.defaultString(request.getParameter(HealthCheckConstants.PARAM_COMBINE_TAGS_OR), "true");

        HealthCheckExecutionOptions options = new HealthCheckExecutionOptions();
        options.setCombineTagsWithOr(Boolean.parseBoolean(combineTagsOr));
        options.setForceInstantExecution(true);
        List<HealthCheckExecutionResult> results = healthCheckExecutor.execute(options, tags);

        boolean allOk = true;
        for (HealthCheckExecutionResult result : results) {
            if (!result.getHealthCheckResult().isOk()) {
                allOk = false;
                break;
            }
        }

        if (!allOk) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        JSONObject resultJson = getJsonObject(results);
        response.getWriter().write(resultJson.toString());
    }

    private JSONObject getJsonObject(List<HealthCheckExecutionResult> results) {
        JSONObject resultJson = new JSONObject();
        try {
            generateResponse(results, resultJson);
        } catch (JSONException ex) {
            logger.error("Could not serialize result into JSON", ex);
        }
        return resultJson;
    }
}
