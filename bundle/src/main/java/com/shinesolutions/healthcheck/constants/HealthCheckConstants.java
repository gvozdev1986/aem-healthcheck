package com.shinesolutions.healthcheck.constants;

import org.osgi.framework.Constants;

public class HealthCheckConstants {

    public static final String REGEX_TAG_FORMAT = "[, ;]+";
    public static final String PARAM_TAGS = "tags";
    public static final String PARAM_COMBINE_TAGS_OR = "combineTagsOr";

    public static final String MESSAGE_LOG_FORMAT = "%s %s";

    public static final String JSON_PARAM_NAME = "name";
    public static final String JSON_PARAM_STATUS = "status";
    public static final String JSON_PARAM_TIME_MS = "timeMs";
    public static final String JSON_PARAM_MESSAGE_LOG = "messageLog";
    public static final String JSON_PARAM_RESULTS = "results";
    public static final String JSON_PARAM_M_BEAN_NAME = "mBeanName";
    public static final String JSON_PARAM_TITLE = "title";
    public static final String JSON_PARAM_SERVICE_ID = "serviceId";
    public static final String JSON_PARAM_TAGS = "tags";
    public static final String JSON_PARAM_ASYNC_CRON_EXPRESSION = "asyncCronExpression";
    public static final String JSON_PARAM_HEALTH_CHECK_METADATA = "healthCheckMetadata";

    public static final String APPLICATION_JSON = "application/json";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String CACHE_CONTROL_VALUE = "must-revalidate,no-cache,no-store";

    public static final int MAX_REPLICATION_TRIES = 3;

    public static final String BUNDLE_FRAGMENT_HOST = Constants.FRAGMENT_HOST;
    public static final String BUNDLE_ACTIVATION_POLICY = Constants.BUNDLE_ACTIVATIONPOLICY;
    public static final String LAZY_ACTIVATION_POLICY = "lazy";

    private HealthCheckConstants() {
    }
}
