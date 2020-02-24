package com.hvozdzeu.healthcheck.hc.impl;

import java.util.Arrays;
import java.util.Dictionary;

import com.hvozdzeu.healthcheck.constants.HealthCheckConstants;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.hc.annotations.SlingHealthCheck;
import org.apache.sling.hc.api.HealthCheck;
import org.apache.sling.hc.api.Result;
import org.apache.sling.hc.util.FormattingResultLog;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;

@SlingHealthCheck(
    name = "Bundle Health Check",
    mbeanName = "bundleHC",
    description = "This health check scans the current OSGi bundles and reports if there is any inactive bundles.",
    tags = {"deep"}
)
public class ActiveBundleHealthCheck implements HealthCheck {

    @Property(label = "Ignored Bundles", description = "The bundles that will be ignored in the Active Bundle Health-Check")
    static final String IGNORED_BUNDLES = "bundles.ignored";

    private BundleContext bundleContext;
    private String[] ignoredBundles;

    private static boolean isActiveBundle(Bundle bundle) {
        return (bundle.getState() == Bundle.ACTIVE ||
            bundle.getHeaders().get(HealthCheckConstants.BUNDLE_FRAGMENT_HOST) != null) ||
            (bundle.getHeaders().get(HealthCheckConstants.BUNDLE_ACTIVATION_POLICY) != null &&
                bundle.getHeaders().get(HealthCheckConstants.BUNDLE_ACTIVATION_POLICY).equals(HealthCheckConstants.LAZY_ACTIVATION_POLICY));
    }

    @Activate
    protected void activate(ComponentContext context) {
        bundleContext = context.getBundleContext();
        Dictionary properties = context.getProperties();

        if (properties != null) {
            ignoredBundles = PropertiesUtil.toStringArray(properties.get(IGNORED_BUNDLES));
        } else {
            ignoredBundles = new String[] {};
        }

    }

    @Deactivate
    protected void deactivate() {
        bundleContext = null;
    }

    @Override
    public Result execute() {
        FormattingResultLog resultLog = new FormattingResultLog();
        int inactiveBundles = 0;
        Bundle[] bundles = bundleContext.getBundles();

        for (Bundle bundle : bundles) {
            if ((!isActiveBundle(bundle)) && !isIgnoredBundle(bundle)) {
                inactiveBundles++;
                resultLog.warn("Bundle {} is not active. It is in state {}.", bundle.getSymbolicName(), bundle.getState());
            }
        }

        if (ignoredBundles != null) {
            resultLog.debug("The following bundles will be ignored: {}", Arrays.toString(ignoredBundles));
        }

        if (inactiveBundles > 0) {
            resultLog.warn("There are {} inactive Bundles", inactiveBundles);
        } else {
            resultLog.info("All bundles are considered active");
        }

        return new Result(resultLog);
    }

    private boolean isIgnoredBundle(Bundle bundle) {
        return (ignoredBundles != null &&
            Arrays.asList(ignoredBundles).contains(bundle.getSymbolicName()));
    }
}
