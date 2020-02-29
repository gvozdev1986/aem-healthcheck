package com.hvozdzeu.healthcheck.models;

import com.adobe.xfa.ut.StringUtils;
import com.google.common.collect.ImmutableList;
import com.hvozdzeu.healthcheck.beans.LangProperties;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class LanguageModel extends LangProperties {

    private static final ImmutableList<String> LANG_LIST = ImmutableList.of("en", "ru");
    private static final String LANG_PATH = "/content/healthcheckreport/jcr:content/data/lang/%s";
    @SlingObject
    private ResourceResolver resourceResolver;

    @Self
    private SlingHttpServletRequest request;

    private ValueMap map;

    @PostConstruct
    protected void init() {
        String lang = request.getParameter("lang");
        getLangMap(LANG_LIST.contains(lang) ? !StringUtils.isEmpty(lang) ? lang : "en" : "en");
    }

    private void getLangMap(String lang) {
        Resource langResource = resourceResolver.getResource(String.format(LANG_PATH, lang));
        if (langResource != null) {
            map = langResource.getValueMap();
        }
    }

    @Override
    public String getLblDate() {
        return map.get("lbl_date", String.class);
    }

    @Override
    public String getLblHcVersion() {
        return map.get("lbl_hcVersion", String.class);
    }

    @Override
    public String getLblInstInformation() {
        return map.get("lbl_instInformation", String.class);
    }

    @Override
    public String getLblInstVersion() {
        return map.get("lbl_instVersion", String.class);
    }

    @Override
    public String getLblUserId() {
        return map.get("lbl_userId", String.class);
    }

}