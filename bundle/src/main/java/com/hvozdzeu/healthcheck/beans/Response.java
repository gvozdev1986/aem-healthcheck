package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private List<Result> results;
    private Total total;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Total getTotal() {
        return total;
    }

    public void setTotal(Total total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Response{" +
                "results=" + results +
                ", total=" + total +
                '}';
    }
}
