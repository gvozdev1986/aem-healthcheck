package com.hvozdzeu.healthcheck.models;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hvozdzeu.healthcheck.beans.Items;
import com.hvozdzeu.healthcheck.beans.Response;
import com.hvozdzeu.healthcheck.beans.Total;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Model;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Model(adaptables = SlingHttpServletRequest.class)
public class TotalReportModel {

    private static final String HTTPS_REQUEST = "http://localhost:4502/system/health";
    private Total total;
    private Response response;

    @PostConstruct
    private void init() throws IOException {
        String json = getRequest();
        buildTotal(json);
    }

    private String getRequest() throws IOException {
        URL url = new URL(HTTPS_REQUEST);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(HttpConstants.METHOD_GET);
        con.setDoOutput(true);
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        return br.lines().collect(Collectors.joining());
    }

    private void buildTotal(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
            response = mapper.readValue(json, Response.class);
            total = response.getTotal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Total getTotal() {
        return total;
    }

    public Response getResponse() {
        return response;
    }
}
