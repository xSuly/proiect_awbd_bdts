package com.cars.microservices.dashboard_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiclePageWrapper {
    private List<Vehicle> content;

    public List<Vehicle> getContent() {
        return content;
    }

    public void setContent(List<Vehicle> content) {
        this.content = content;
    }
}
