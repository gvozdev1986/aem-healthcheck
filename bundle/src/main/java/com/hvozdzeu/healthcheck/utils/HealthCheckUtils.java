package com.hvozdzeu.healthcheck.utils;

import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.CRITICAL_TYPE;
import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.DEBUG_TYPE;
import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.HEALTH_CHECK_ERROR_TYPE;
import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.IMPORTANT_TYPE;
import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.INFO_TYPE;
import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.OK_TYPE;
import static com.hvozdzeu.healthcheck.constants.HealthCheckConstants.WARN_TYPE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hvozdzeu.healthcheck.beans.Total;
import org.apache.sling.hc.api.ResultLog;

public class HealthCheckUtils {

    private HealthCheckUtils() {
    }

    public static List<ResultLog.Entry> getResultList(Iterator<ResultLog.Entry> entryIterator) {
        List<ResultLog.Entry> actualList = new ArrayList<>();
        while (entryIterator.hasNext()) {
            actualList.add(entryIterator.next());
        }
        return actualList;
    }

    public static void buildMapCountStatus(Map<String, Integer> totalResultMap, String status) {
        switch (status) {
            case WARN_TYPE:
            case INFO_TYPE:
            case OK_TYPE:
            case IMPORTANT_TYPE:
            case CRITICAL_TYPE:
            case HEALTH_CHECK_ERROR_TYPE:
            case DEBUG_TYPE: {
                checkType(totalResultMap, status);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }
    }

    private static void checkType(Map<String, Integer> totalResultMap, String status) {
        Integer currentCount = totalResultMap.get(status);
        currentCount++;
        totalResultMap.put(status, currentCount);
    }

    public static Total buildTotalResult(Map<String, Integer> totalResultMap) {
        Integer count = 0;
        for (Map.Entry<String, Integer> entry : totalResultMap.entrySet()) {
            count = count + entry.getValue();
        }
        Total total = new Total();
        total.setTotalItems(count);
        total.setWarn(totalResultMap.get(WARN_TYPE));
        total.setInfo(totalResultMap.get(INFO_TYPE));
        total.setOk(totalResultMap.get(OK_TYPE));
        total.setImportant(totalResultMap.get(IMPORTANT_TYPE));
        total.setCritical(totalResultMap.get(CRITICAL_TYPE));
        total.setHealthCheckError(totalResultMap.get(HEALTH_CHECK_ERROR_TYPE));
        total.setDebug(totalResultMap.get(DEBUG_TYPE));
        return total;
    }

    public static String buildMessagesUrl(int i) {
        return String.format("http://%s:%s/content/healthcheckreport/jcr:content.report.json?reportId=%s", "localhost", "4502", i);
    }

    public static String buildBackUrl() {
        return String.format("http://%s:%s/system/health", "localhost", "4502");
    }

    public static void initTotalMap(Map<String, Integer> totalMap) {
        totalMap.put(WARN_TYPE, 0);
        totalMap.put(INFO_TYPE, 0);
        totalMap.put(OK_TYPE, 0);
        totalMap.put(IMPORTANT_TYPE, 0);
        totalMap.put(CRITICAL_TYPE, 0);
        totalMap.put(HEALTH_CHECK_ERROR_TYPE, 0);
        totalMap.put(DEBUG_TYPE, 0);
    }

}
