package com.hvozdzeu.healthcheck.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private List<Items> items;
    private Total total;

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
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
            "healthCheckResult=" + items +
            ", healthCheckTotalResult=" + total +
            '}';
    }
}
