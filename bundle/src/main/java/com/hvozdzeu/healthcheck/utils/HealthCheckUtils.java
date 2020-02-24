package com.hvozdzeu.healthcheck.utils;

import org.apache.sling.hc.api.ResultLog;

import java.util.*;

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
            case "WARN": {
                Integer currentCount = totalResultMap.get("WARN");
                currentCount = currentCount + 1;
                totalResultMap.put("WARN", currentCount);
                break;
            }
            case "INFO": {
                Integer currentCount = totalResultMap.get("INFO");
                currentCount = currentCount + 1;
                totalResultMap.put("INFO", currentCount);
                break;
            }
            case "OK": {
                Integer currentCount = totalResultMap.get("OK");
                currentCount = currentCount + 1;
                totalResultMap.put("OK", currentCount);
                break;
            }
            case "IMPORTANT": {
                Integer currentCount = totalResultMap.get("IMPORTANT");
                currentCount = currentCount + 1;
                totalResultMap.put("IMPORTANT", currentCount);
                break;
            }
            case "CRITICAL": {
                Integer currentCount = totalResultMap.get("CRITICAL");
                currentCount = currentCount + 1;
                totalResultMap.put("CRITICAL", currentCount);
                break;
            }
            case "HEALTH_CHECK_ERROR": {
                Integer currentCount = totalResultMap.get("HEALTH_CHECK_ERROR");
                currentCount = currentCount + 1;
                totalResultMap.put("HEALTH_CHECK_ERROR", currentCount);
                break;
            }
        }
    }

    public static Map<String, Integer> buildTotalResult(Map<String, Integer> totalResultMap) {
        Map<String, Integer> map = new HashMap<>();
        Integer count = totalResultMap.get("WARN") + totalResultMap.get("INFO") + totalResultMap.get("OK") +
                totalResultMap.get("IMPORTANT") + totalResultMap.get("CRITICAL") + totalResultMap.get("HEALTH_CHECK_ERROR");
        map.put("total", count);
        map.put("WARN", totalResultMap.get("WARN"));
        map.put("INFO", totalResultMap.get("INFO"));
        map.put("OK", totalResultMap.get("OK"));
        map.put("IMPORTANT", totalResultMap.get("IMPORTANT"));
        map.put("CRITICAL", totalResultMap.get("CRITICAL"));
        map.put("HEALTH_CHECK_ERROR", totalResultMap.get("HEALTH_CHECK_ERROR"));
        return map;
    }

}
