package com.shinesolutions.healthcheck.utils;

import org.apache.sling.hc.api.ResultLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

}
